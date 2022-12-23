package ch.senegal.engine.xml

import ch.senegal.engine.logger.SenegalLogger
import ch.senegal.engine.model.MutableModelInstance
import ch.senegal.engine.model.MutableModelNode
import ch.senegal.engine.model.MutableModelTree
import ch.senegal.engine.model.converter.FacetValueConverter
import ch.senegal.engine.plugin.resolver.ResolvedConcept
import ch.senegal.engine.plugin.resolver.ResolvedPlugins
import ch.senegal.engine.util.CaseUtil
import ch.senegal.engine.util.PlaceholderUtil
import ch.senegal.engine.virtualfilesystem.VirtualFileSystem
import ch.senegal.plugin.ConceptName
import ch.senegal.plugin.PurposeFacetCombinedName
import org.xml.sax.Attributes
import org.xml.sax.InputSource
import org.xml.sax.SAXException
import org.xml.sax.SAXParseException
import org.xml.sax.ext.DefaultHandler2
import java.nio.file.Path
import java.util.*
import kotlin.io.path.absolutePathString


class SenegalSaxParserHandler(
    private val resolvedPlugins: ResolvedPlugins,
    private val modelTree: MutableModelTree,
    private val placeholders: Map<String, String>,
    private val schemaFileDirectory: Path,
    private val virtualFileSystem: VirtualFileSystem,
    private val senegalLogger: SenegalLogger,
) : DefaultHandler2() {
    private var currentMutableModelInstance: MutableModelInstance = modelTree
    private var isInDefinitionTag = false
    private val configurations: MutableMap<PurposeFacetCombinedName, String> = mutableMapOf()

    @Throws(SAXException::class)
    override fun startElement(uri: String, localName: String, qName: String, attr: Attributes) {
        val attributes = Attribute.attributeList(attr)
        senegalLogger.logDebug { "XML: startElement: localName:$localName, attributes: $attributes" }
        if(isInDefinitionTag) {
            val resolvedConcept = getConceptByXmlLocalName(localName) ?: return
            val newModelNode = currentMutableModelInstance.createAndAddMutableModelNode(resolvedConcept)
            addAttributes(
                mutableModelNode = newModelNode,
                attributes = attributes,
            )
            this.currentMutableModelInstance = newModelNode
            return
        }

        when(localName) {
            "configuration" -> attributes.forEach { addConfigurationAttribute(it) }
            "definitions" -> isInDefinitionTag = true
        }
    }

    private fun addAttributes(mutableModelNode: MutableModelNode, attributes: List<Attribute>) {
        val attributeMap: Map<PurposeFacetCombinedName, Attribute> = attributes
            .associateBy { PurposeFacetCombinedName.of(CaseUtil.capitalize(it.localName)) }

        mutableModelNode.resolvedConcept.enclosedFacets.forEach { resolvedFacet ->
            val rawAttributeValue: String = attributeMap[resolvedFacet.purposeFacetName]?.value
                ?: configurations[resolvedFacet.purposeFacetName]
                ?: return@forEach

            val attributeValue = PlaceholderUtil.replacePlaceholders(rawAttributeValue, placeholders)
            val facetValue = FacetValueConverter.convertString(resolvedFacet.facet, attributeValue)
            mutableModelNode.addFacetValue(resolvedFacet = resolvedFacet, facetValue = facetValue)
        }
    }

    private fun addConfigurationAttribute(attribute: Attribute) {
        val purposeFacetCombinedName = PurposeFacetCombinedName.of(CaseUtil.capitalize(attribute.localName))
        configurations[purposeFacetCombinedName] = attribute.value
    }


    @Throws(SAXException::class)
    override fun endElement(uri: String, localName: String, qName: String) {
        senegalLogger.logDebug { "XML: endElement: localName:$localName" }
        if(localName == "definitions") {
            isInDefinitionTag = false
        }
        if(isConcept(localName)) {
            this.currentMutableModelInstance = currentMutableModelInstance.parentMutableModelInstance() ?: modelTree
        }
    }


    private fun isConcept(localName: String): Boolean {
        return getConceptByXmlLocalName(localName) != null
    }

    private fun getConceptByXmlLocalName(localName: String): ResolvedConcept? {
        val potentialConceptName = ConceptName.of(CaseUtil.capitalize(localName))
        return resolvedPlugins.getConceptByConceptName(potentialConceptName)
    }

    override fun fatalError(e: SAXParseException) {
        throw e
    }

    override fun error(e: SAXParseException) {
        throw e
    }

    override fun warning(e: SAXParseException) {
         senegalLogger.logWarnings(e.message ?: e.toString())
    }

    override fun resolveEntity(name: String?, publicId: String?, baseURI: String?, systemId: String?): InputSource? {
        senegalLogger.logDebug { "XML: resolveEntity: systemId:$systemId, publicId:$publicId, baseURI:$baseURI" }
        return if(systemId != null && systemId.startsWith("./")) {
            InputSource(virtualFileSystem.fileAsInputStream(schemaFileDirectory.resolve(systemId).normalize()))
        } else {
            super.resolveEntity(name, publicId, baseURI, systemId)
        }
    }
}
