package ch.cassiamon.xml.schemagic.parser

import ch.cassiamon.pluginapi.schema.ConceptSchema
import ch.cassiamon.engine.schema.Schema
import ch.cassiamon.engine.util.PlaceholderUtil
import ch.cassiamon.pluginapi.filesystem.FileSystemAccess
import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName
import ch.cassiamon.pluginapi.logger.LoggerFacade
import ch.cassiamon.pluginapi.model.ConceptIdentifier
import ch.cassiamon.pluginapi.registration.InputSourceConceptFacetValueBuilder
import ch.cassiamon.pluginapi.registration.InputSourceDataCollector
import ch.cassiamon.pluginapi.schema.SchemaAccess
import org.xml.sax.Attributes
import org.xml.sax.InputSource
import org.xml.sax.SAXException
import org.xml.sax.SAXParseException
import org.xml.sax.ext.DefaultHandler2
import java.nio.file.Path
import java.util.*


class SaxParserHandler(
    private val schema: SchemaAccess,
    private val dataCollector: InputSourceDataCollector,
    private val placeholders: Map<String, String>,
    private val schemaFileDirectory: Path,
    private val fileSystemAccess: FileSystemAccess,
    private val logger: LoggerFacade,
) : DefaultHandler2() {

    private var conceptIdentifierStack: MutableList<ConceptIdentifier> = mutableListOf()
    private var isInDefinitionTag = false
    private val configurations: MutableMap<FacetName, String> = mutableMapOf()

    @Throws(SAXException::class)
    override fun startElement(uri: String, localName: String, qName: String, attr: Attributes) {
        val xmlAttributes = XmlAttribute.attributeList(attr)
        logger.logDebug { "XML: startElement: localName:$localName, attributes: $xmlAttributes" }

        if(isInDefinitionTag) {
            val conceptSchema = getConceptByXmlLocalName(localName) ?: return
            val conceptIdentifier = ConceptIdentifier.random() // TODO
            val parentConceptIdentifier = conceptIdentifierStack.lastOrNull()
            val currentConceptBuilder = dataCollector.newConceptData(conceptSchema.conceptName, conceptIdentifier, parentConceptIdentifier)
            addAttributes(conceptSchema, currentConceptBuilder, xmlAttributes)
            currentConceptBuilder.attach()
            this.conceptIdentifierStack.add(conceptIdentifier)
            return
        }

        when(localName) {
            "configuration" -> xmlAttributes.forEach { addConfigurationAttribute(it) }
            "definitions" -> isInDefinitionTag = true
        }
    }

    private fun addAttributes(conceptSchema: ConceptSchema, conceptBuilder: InputSourceConceptFacetValueBuilder, xmlAttributes: List<XmlAttribute>) {
        val attributeMap: Map<FacetName, XmlAttribute> = xmlAttributes
            .associateBy { FacetName.of(CaseUtil.capitalize(it.localName)) }

        conceptSchema.inputFacets.forEach { inputFacetSchema ->
            val schemaFacetName = inputFacetSchema.inputFacet.facetName
            val rawAttributeValue = attributeMap[schemaFacetName]?.value
                ?: configurations[schemaFacetName]
                ?: return@forEach

            val attributeValue = PlaceholderUtil.replacePlaceholders(rawAttributeValue, placeholders)
            val facetValue = XmlFacetValueConverter.convertString(inputFacetSchema, attributeValue)

            conceptBuilder.addFacetValue(facetValue)

        }
    }

    private fun addConfigurationAttribute(attribute: XmlAttribute) {
        val facetName = FacetName.of(CaseUtil.capitalize(attribute.localName))
        configurations[facetName] = attribute.value
    }


    @Throws(SAXException::class)
    override fun endElement(uri: String, localName: String, qName: String) {
        logger.logDebug { "XML: endElement: localName:$localName" }
        if(localName == "definitions") {
            isInDefinitionTag = false
        }
        if(isConcept(localName)) {
            this.conceptIdentifierStack.removeLast() // pop last element from stack
        }
    }


    private fun isConcept(localName: String): Boolean {
        return getConceptByXmlLocalName(localName) != null
    }

    private fun getConceptByXmlLocalName(localName: String): ConceptSchema? {
        val potentialConceptName = ConceptName.of(CaseUtil.capitalize(localName))
        return if(schema.hasConceptName(potentialConceptName)) {
            schema.conceptByConceptName(potentialConceptName)
        } else {
            null
        }

    }

    override fun fatalError(e: SAXParseException) {
        throw e
    }

    override fun error(e: SAXParseException) {
        throw e
    }

    override fun warning(e: SAXParseException) {
         logger.logWarnings(e.message ?: e.toString())
    }

    override fun resolveEntity(name: String?, publicId: String?, baseURI: String?, systemId: String?): InputSource? {
        logger.logDebug { "XML: resolveEntity: systemId:$systemId, publicId:$publicId, baseURI:$baseURI" }
        return if(systemId != null && systemId.startsWith("./")) {
            InputSource(fileSystemAccess.fileAsInputStream(schemaFileDirectory.resolve(systemId).normalize()))
        } else {
            super.resolveEntity(name, publicId, baseURI, systemId)
        }
    }
}
