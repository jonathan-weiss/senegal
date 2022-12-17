package ch.senegal.engine.xml

import ch.senegal.engine.model.MutableModelTree
import ch.senegal.engine.plugin.TestPluginFinder
import ch.senegal.engine.plugin.resolver.PluginResolver
import ch.senegal.plugin.PurposeFacetCombinedName
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.nio.file.Paths
import javax.xml.parsers.SAXParser
import javax.xml.parsers.SAXParserFactory


internal class SenegalSaxParserHandlerTest {

    private val testXml = """
        <?xml version="1.0" encoding="utf-8" ?>
        <senegal xmlns="https://senegal.ch/senegal"
                 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                 xsi:schemaLocation="https://senegal.ch/senegal senegal-schema.xsd">
            <configuration testKotlinModelPackage="ch.senegal.entities"/>
            <definitions>
                <testEntity testEntityName="Person" testKotlinModelClassname="Person">
                    <testEntityAttribute testEntityAttributeName="firstname" testEntityAttributeType="TEXT" testKotlinFieldType="kotlin.String" />
                    <testEntityAttribute testEntityAttributeName="lastname" testEntityAttributeType="NUMBER" testKotlinFieldType="kotlin.Int"/>
                    <testEntityAttribute testEntityAttributeName="nickname" testEntityAttributeType="BOOLEAN" testKotlinFieldType="kotlin.Boolean"/>
                </testEntity>
                <testEntity testEntityName="Address">
                    <testEntityAttribute testEntityAttributeName="street" testEntityAttributeType="TEXT"/>
                    <testEntityAttribute testEntityAttributeName="zip" testEntityAttributeType="TEXT"/>
                </testEntity>
            </definitions>
        </senegal>
    """.trimIndent()


    @Test
    fun testSaxParser() {
        val factory: SAXParserFactory = SAXParserFactory.newInstance()
        factory.isNamespaceAware = true
        factory.isValidating = false // turn of validation as schema is not found
        val saxParser: SAXParser = factory.newSAXParser()
        val plugins = TestPluginFinder.findAllTestPlugins()
        val resolvedPlugins = PluginResolver.resolvePlugins(plugins)
        val modelTree = MutableModelTree(resolvedPlugins)
        val senegalSaxParser = SenegalSaxParserHandler(resolvedPlugins, modelTree, emptyMap(), Paths.get("."))

        testXml.byteInputStream().use {
            saxParser.parse(it, senegalSaxParser)
        }

        assertEquals(2, modelTree.getRootModelNodes().size)

        val personRootNode = modelTree.getRootModelNodes().first()
        assertEquals("Person", personRootNode.nodeFacetValues[PurposeFacetCombinedName.of("TestEntityName")]?.value)
        assertEquals("Person", personRootNode.nodeFacetValues[PurposeFacetCombinedName.of("TestKotlinModelClassname")]?.value)
        assertEquals("ch.senegal.entities", personRootNode.nodeFacetValues[PurposeFacetCombinedName.of("TestKotlinModelPackage")]?.value)
        assertEquals(3, personRootNode.mutableChildModelNodes.size)
        val firstnameNode = personRootNode.mutableChildModelNodes[0]
        assertEquals("firstname", firstnameNode.nodeFacetValues[PurposeFacetCombinedName.of("TestEntityAttributeName")]?.value)
        assertEquals("TEXT", firstnameNode.nodeFacetValues[PurposeFacetCombinedName.of("TestEntityAttributeType")]?.value)
        assertEquals("kotlin.String", firstnameNode.nodeFacetValues[PurposeFacetCombinedName.of("TestKotlinFieldType")]?.value)

        val addressRootNode = modelTree.getRootModelNodes().last()
        assertEquals("Address", addressRootNode.nodeFacetValues[PurposeFacetCombinedName.of("TestEntityName")]?.value)
        assertEquals("ch.senegal.entities", addressRootNode.nodeFacetValues[PurposeFacetCombinedName.of("TestKotlinModelPackage")]?.value)
        assertEquals(2, addressRootNode.mutableChildModelNodes.size)
    }
}
