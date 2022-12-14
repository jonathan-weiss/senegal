package ch.senegal.engine.xml

import ch.senegal.engine.model.MutableModelTree
import ch.senegal.engine.plugin.TestPluginFinder
import ch.senegal.engine.plugin.resolver.PluginResolver
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import javax.xml.parsers.SAXParser
import javax.xml.parsers.SAXParserFactory


internal class SenegalSaxParserHandlerTest {

    private val testXml = """
        <?xml version="1.0" encoding="utf-8" ?>
        <senegal xmlns="https://senegal.ch/senegal"
                 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                 xsi:schemaLocation="https://senegal.ch/senegal senegal-schema.xsd">
            <testEntity testEntityName="Person" testKotlinModelClassname="Person" testKotlinModelPackage="ch.senegal.person">
                <testEntityAttribute testEntityAttributeName="firstname" testEntityAttributeType="TEXT" testKotlinFieldType="kotlin.String" />
                <testEntityAttribute testEntityAttributeName="lastname" testEntityAttributeType="NUMBER" testKotlinFieldType="kotlin.Int"/>
                <testEntityAttribute testEntityAttributeName="nickname" testEntityAttributeType="BOOLEAN" testKotlinFieldType="kotlin.Boolean"/>
            </testEntity>
            <testEntity testEntityName="Address">
                <testEntityAttribute testEntityAttributeName="street" testEntityAttributeType="TEXT"/>
                <testEntityAttribute testEntityAttributeName="zip" testEntityAttributeType="TEXT"/>
            </testEntity>
        </senegal>
    """.trimIndent()


    @Test
    fun testSaxParser() {
        val factory: SAXParserFactory = SAXParserFactory.newInstance()
        factory.isNamespaceAware = true
        factory.isValidating = true
        val saxParser: SAXParser = factory.newSAXParser()
        val plugins = TestPluginFinder.findAllTestPlugins()
        val resolvedPlugins = PluginResolver.resolvePlugins(plugins)
        val modelTree = MutableModelTree(resolvedPlugins)
        val senegalSaxParser = SenegalSaxParserHandler(resolvedPlugins, modelTree, emptyMap())
        val saxParserHandler = DelegatingToManySaxHandler(listOf(PrinterHelperSaxHandler(), senegalSaxParser))

        testXml.byteInputStream().use {
            saxParser.parse(it, saxParserHandler)
        }

        assertEquals(2, modelTree.getRootModelNodes().size)
//        val personRootNode = modelTree.getRootModelNodes().first()
//        val addressRootNode = modelTree.getRootModelNodes().last()
//
//        assertEquals("Person", personRootNode.properties["name"])
//        assertEquals("Person", personRootNode.properties["kotlin-model-class-name"])
//        assertEquals("ch.senegal.person", personRootNode.properties["kotlin-model-package"])
//        assertEquals(3, personRootNode.childNodes.size)
//        val firstnameNode = personRootNode.childNodes[0]
//        assertEquals("firstname", firstnameNode.properties["name"])
//        assertEquals("TEXT", firstnameNode.properties["type"])
//        assertEquals("kotlin.String", firstnameNode.properties["testKotlinFieldType"])
//
//        assertEquals("Address", addressRootNode.properties["name"])
//        assertEquals(2, addressRootNode.childNodes.size)
//        val zipNode = addressRootNode.childNodes[0]
//        assertEquals("TEXT", zipNode.properties["type"])
        //assertEquals("kotlin.String", zipNode.properties["testKotlinFieldType"])
    }
}
