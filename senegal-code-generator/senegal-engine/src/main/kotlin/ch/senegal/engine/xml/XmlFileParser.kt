package ch.senegal.engine.xml

import ch.senegal.engine.model.MutableModelTree
import ch.senegal.engine.plugin.resolver.ResolvedPlugins
import java.nio.file.Path
import javax.xml.XMLConstants
import javax.xml.parsers.SAXParser
import javax.xml.parsers.SAXParserFactory
import javax.xml.validation.SchemaFactory
import kotlin.io.path.inputStream


object XmlFileParser {

    private const val schemaLanguage = XMLConstants.W3C_XML_SCHEMA_NS_URI
    private const val schemaFeature = "http://apache.org/xml/features/validation/schema"

    fun validateAndReadXmlFile(
        resolvedPlugins: ResolvedPlugins,
        xmlDefinitionFile: Path,
        placeholders: Map<String, String>
    ): MutableModelTree {

        val factory: SAXParserFactory = SAXParserFactory.newInstance()
        factory.isNamespaceAware = true
        factory.isValidating = false
        factory.setFeature(schemaFeature,true)
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true)
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, false)

        val senegalSchemaXsd = xmlDefinitionFile.parent.resolve("schema").resolve("senegal-schema.xsd")
        val schemaFactory: SchemaFactory = SchemaFactory.newInstance(schemaLanguage)
        val xsdFile = senegalSchemaXsd.toFile()
        factory.schema = schemaFactory.newSchema(xsdFile)


        val saxParser: SAXParser = factory.newSAXParser()

        val modelTree = MutableModelTree(resolvedPlugins)
        val senegalSaxParser = SenegalSaxParserHandler(resolvedPlugins, modelTree, placeholders, xmlDefinitionFile.parent)

        xmlDefinitionFile.inputStream().use {
            saxParser.parse(it, senegalSaxParser)
        }
        return modelTree
    }
}
