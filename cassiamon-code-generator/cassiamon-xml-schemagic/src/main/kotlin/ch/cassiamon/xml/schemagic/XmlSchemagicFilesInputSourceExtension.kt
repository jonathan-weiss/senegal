package ch.cassiamon.xml.schemagic

import ch.cassiamon.api.extensions.ExtensionName
import ch.cassiamon.api.extensions.inputsource.ConceptAndFacetDataCollector
import ch.cassiamon.api.extensions.inputsource.files.FilesInputSourceExtension
import ch.cassiamon.api.filesystem.FileSystemAccess
import ch.cassiamon.api.logger.LoggerFacade
import ch.cassiamon.api.parameter.ParameterAccess
import ch.cassiamon.api.schema.SchemaAccess
import ch.cassiamon.xml.schemagic.parser.SaxParserHandler
import ch.cassiamon.xml.schemagic.schemacreator.XmlSchemaInitializer
import java.nio.file.Path
import javax.xml.XMLConstants
import javax.xml.parsers.SAXParser
import javax.xml.parsers.SAXParserFactory
import javax.xml.transform.stream.StreamSource
import javax.xml.validation.SchemaFactory
import kotlin.io.path.name

class XmlSchemagicFilesInputSourceExtension: FilesInputSourceExtension {

    private lateinit var schemaAccess: SchemaAccess;
    private lateinit var conceptAndFacetDataCollector: ConceptAndFacetDataCollector;
    private lateinit var fileSystemAccess: FileSystemAccess;
    private lateinit var loggerFacade: LoggerFacade;
    private lateinit var parameterAccess: ParameterAccess;

    companion object {
        private val extensionName = ExtensionName.of("XmlSchemagicInputExtension")
        private const val schemaLanguage = XMLConstants.W3C_XML_SCHEMA_NS_URI
        private const val schemaFeature = "http://apache.org/xml/features/validation/schema"
    }

    override fun getExtensionName(): ExtensionName {
        return extensionName
    }

    override fun initializeExtension(loggerFacade: LoggerFacade, parameterAccess: ParameterAccess) {
        this.loggerFacade = loggerFacade
        this.parameterAccess = parameterAccess
    }

    override fun initializeInputSourceExtension(
        conceptAndFacetDataCollector: ConceptAndFacetDataCollector,
        fileSystemAccess: FileSystemAccess
    ) {
        this.fileSystemAccess = fileSystemAccess
        this.conceptAndFacetDataCollector = conceptAndFacetDataCollector
    }

    override fun initializeSchema(schemaAccess: SchemaAccess) {
        this.schemaAccess = schemaAccess
    }

    override fun readFromFiles(files: Set<Path>) {
        files.forEach { readFromFile(it) }
    }

    private fun readFromFile(file: Path) {
        val xmlDefinitionDirectory: Path = file.parent
        val xmlDefinitionFilename = file.name
        val xmlDefinitionFile = xmlDefinitionDirectory.resolve(xmlDefinitionFilename)
        val schemaDirectory = XmlSchemaInitializer.createSchemaDirectory(xmlDefinitionDirectory, fileSystemAccess)

        XmlSchemaInitializer.initializeXmlSchemaFile(schemaDirectory, schemaAccess, fileSystemAccess)


        val placeholders: Map<String, String> = parameterAccess.getParameterMap()

        val factory: SAXParserFactory = SAXParserFactory.newInstance()
        factory.isNamespaceAware = true
        factory.isValidating = false
        factory.setFeature(schemaFeature, true)
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true)
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, false)

        val senegalSchemaXsd = xmlDefinitionFile.parent.resolve("schema").resolve("cassiamon-schemagic-schema.xsd")
        val sources = listOf<StreamSource>(
            StreamSource(fileSystemAccess.fileAsInputStream(senegalSchemaXsd))
        )

        val schemaFactory: SchemaFactory = SchemaFactory.newInstance(schemaLanguage)
        factory.schema = schemaFactory.newSchema(sources.toTypedArray())

        val saxParser: SAXParser = factory.newSAXParser()

        val saxParserHandler = SaxParserHandler(schemaAccess, conceptAndFacetDataCollector, placeholders, xmlDefinitionDirectory, fileSystemAccess, loggerFacade)

        fileSystemAccess.fileAsInputStream(xmlDefinitionFile).use {
            saxParser.parse(it, saxParserHandler)
        }
    }
}
