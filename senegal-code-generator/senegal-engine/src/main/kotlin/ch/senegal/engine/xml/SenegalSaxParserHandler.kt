package ch.senegal.engine.xml

import ch.senegal.engine.model.MutableModelInstance
import ch.senegal.engine.model.MutableModelNode
import ch.senegal.engine.model.MutableModelTree
import ch.senegal.engine.model.converter.FacetValueConverter
import ch.senegal.engine.plugin.resolver.ResolvedConcept
import ch.senegal.engine.plugin.resolver.ResolvedPlugins
import ch.senegal.engine.util.CaseUtil
import ch.senegal.engine.util.PlaceholderUtil
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
) : DefaultHandler2() {
    private var currentMutableModelInstance: MutableModelInstance = modelTree
    private var isInDefinitionTag = false
    private val configurations: MutableMap<PurposeFacetCombinedName, String> = mutableMapOf()

    @Throws(SAXException::class)
    override fun startElement(uri: String, localName: String, qName: String, attr: Attributes) {
        if(isInDefinitionTag) {
            val resolvedConcept = getConceptByXmlLocalName(localName) ?: return
            val newModelNode = currentMutableModelInstance.createAndAddMutableModelNode(resolvedConcept)
            addAttributes(
                mutableModelNode = newModelNode,
                attributes = Attribute.attributeList(attr),
            )
            this.currentMutableModelInstance = newModelNode
            return
        }

        when(localName) {
            "configuration" -> Attribute.attributeList(attr).forEach { addConfigurationAttribute(it) }
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

    private fun fail(message: String): Nothing {
        throw SAXException(message)
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
         println("Warning: ${e.message}")
    }

    override fun resolveEntity(name: String?, publicId: String?, baseURI: String?, systemId: String?): InputSource? {
        return if(systemId != null && systemId.startsWith("./")) {
            InputSource(schemaFileDirectory.resolve(systemId).absolutePathString())
        } else {
            super.resolveEntity(name, publicId, baseURI, systemId)
        }
    }
}
