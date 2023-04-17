package ch.cassiamon.xml.schematic

import ch.cassiamon.engine.logger.CassiamonLogger
import ch.cassiamon.engine.schema.Schema
import ch.cassiamon.engine.virtualfilesystem.VirtualFileSystem
import ch.cassiamon.pluginapi.registration.InputSourceDataCollector
import java.nio.file.Path
import javax.xml.XMLConstants
import javax.xml.parsers.SAXParser
import javax.xml.parsers.SAXParserFactory
import javax.xml.transform.stream.StreamSource
import javax.xml.validation.SchemaFactory


object XmlFileParser {

    private const val schemaLanguage = XMLConstants.W3C_XML_SCHEMA_NS_URI
    private const val schemaFeature = "http://apache.org/xml/features/validation/schema"

    fun validateAndReadXmlFile(
        schema: Schema,
        dataCollector: InputSourceDataCollector,
        xmlDefinitionFile: Path,
        placeholders: Map<String, String>,
        virtualFileSystem: VirtualFileSystem,
        logger: CassiamonLogger,
    ) {

        val factory: SAXParserFactory = SAXParserFactory.newInstance()
        factory.isNamespaceAware = true
        factory.isValidating = false
        factory.setFeature(schemaFeature, true)
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true)
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, false)

        val senegalSchemaXsd = xmlDefinitionFile.parent.resolve("schema").resolve("senegal-schema.xsd")
        val sources = listOf(
            StreamSource(virtualFileSystem.fileAsInputStream(senegalSchemaXsd))
        )

        val schemaFactory: SchemaFactory = SchemaFactory.newInstance(schemaLanguage)
        factory.schema = schemaFactory.newSchema(sources.toTypedArray())

        val saxParser: SAXParser = factory.newSAXParser()

        val saxParserHandler = SaxParserHandler(schema, dataCollector, placeholders, xmlDefinitionFile.parent, virtualFileSystem, logger)

        virtualFileSystem.fileAsInputStream(xmlDefinitionFile).use {
            saxParser.parse(it, saxParserHandler)
        }
    }
}
