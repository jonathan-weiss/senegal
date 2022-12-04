package ch.senegal.engine.xml

import ch.senegal.engine.plugin.finder.PluginFinder
import ch.senegal.engine.plugin.tree.PluginTreeCreator
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
            <entity name="Person" kotlin-model-class-name="Person" kotlin-model-package="ch.senegal.person">
                <entity-attribute name="firstname" type="TEXT" kotlin-field-field-type="kotlin.String" />
                <entity-attribute name="lastname" type="NUMBER" kotlin-field-field-type="kotlin.Int"/>
                <entity-attribute name="nickname" type="BOOLEAN" kotlin-field-field-type="kotlin.Boolean"/>
            </entity>
            <entity name="Address">
                <entity-attribute name="street" type="TEXT"/>
                <entity-attribute name="zip" type="TEXT"/>
            </entity>
        </senegal>
    """.trimIndent()


    @Test
    fun testSaxParser() {
        val factory: SAXParserFactory = SAXParserFactory.newInstance()
        factory.isNamespaceAware = true
        factory.isValidating = true
        val saxParser: SAXParser = factory.newSAXParser()
        val pluginTree = PluginTreeCreator.createPluginTree(PluginFinder.findAllPlugins())
        val senegalSaxParser = SenegalSaxParserHandler(pluginTree)
        val saxParserHandler = DelegatingToManySaxHandler(listOf(PrinterHelperSaxHandler(), senegalSaxParser))

        testXml.byteInputStream().use {
            saxParser.parse(it, saxParserHandler)
        }

        assertEquals(2, senegalSaxParser.getListOfRootTemplateModelNodes().size)
        val personRootNode = senegalSaxParser.getListOfRootTemplateModelNodes().first()
        val addressRootNode = senegalSaxParser.getListOfRootTemplateModelNodes().last()

        assertEquals("Person", personRootNode.properties["name"])
        assertEquals("Person", personRootNode.properties["kotlin-model-class-name"])
        assertEquals("ch.senegal.person", personRootNode.properties["kotlin-model-package"])
        assertEquals(3, personRootNode.childNodes.size)
        val firstnameNode = personRootNode.childNodes[0]
        assertEquals("firstname", firstnameNode.properties["name"])
        assertEquals("TEXT", firstnameNode.properties["type"])
        assertEquals("kotlin.String", firstnameNode.properties["kotlin-field-field-type"])

        assertEquals("Address", addressRootNode.properties["name"])
        assertEquals(2, addressRootNode.childNodes.size)
        val zipNode = addressRootNode.childNodes[0]
        assertEquals("TEXT", zipNode.properties["type"])
        //assertEquals("kotlin.String", zipNode.properties["kotlin-field-field-type"])
    }
}
