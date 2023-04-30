package ch.cassiamon.xml.schemagic

import ch.cassiamon.pluginapi.filesystem.FileSystemAccess
import ch.cassiamon.pluginapi.logger.LoggerFacade
import ch.cassiamon.pluginapi.parameter.ParameterAccess
import ch.cassiamon.pluginapi.registration.InputSourceDataCollector
import ch.cassiamon.pluginapi.schema.SchemaAccess
import ch.cassiamon.xml.schemagic.parser.SaxParserHandler
import java.nio.file.Path
import javax.xml.XMLConstants
import javax.xml.parsers.SAXParser
import javax.xml.parsers.SAXParserFactory
import javax.xml.transform.stream.StreamSource
import javax.xml.validation.SchemaFactory

object XmlSchemagicFactory {

    private const val schemaLanguage = XMLConstants.W3C_XML_SCHEMA_NS_URI
    private const val schemaFeature = "http://apache.org/xml/features/validation/schema"

    fun parseXml(
        schemaAccess: SchemaAccess,
        dataCollector: InputSourceDataCollector,
        xmlDefinitionFile: Path,
        fileSystemAccess: FileSystemAccess,
        logger: LoggerFacade,
        receiveParameterAccess: ParameterAccess,
                 ) {

        val placeholders: Map<String, String> = receiveParameterAccess.getParameterMap()

        val factory: SAXParserFactory = SAXParserFactory.newInstance()
        factory.isNamespaceAware = true
        factory.isValidating = false
        factory.setFeature(schemaFeature, true)
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true)
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, false)

//        val senegalSchemaXsd = xmlDefinitionFile.parent.resolve("schema").resolve("senegal-schema.xsd")
        val sources = listOf<StreamSource>(
//            StreamSource(fileSystemAccess.fileAsInputStream(senegalSchemaXsd))
        )

        val schemaFactory: SchemaFactory = SchemaFactory.newInstance(schemaLanguage)
        factory.schema = schemaFactory.newSchema(sources.toTypedArray())

        val saxParser: SAXParser = factory.newSAXParser()

        val saxParserHandler = SaxParserHandler(schemaAccess, dataCollector, placeholders, xmlDefinitionFile.parent, fileSystemAccess, logger)

        fileSystemAccess.fileAsInputStream(xmlDefinitionFile).use {
            saxParser.parse(it, saxParserHandler)
        }
    }
}
