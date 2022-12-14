package ch.senegal.engine.xml

import ch.senegal.engine.model.MutableModelInstance
import ch.senegal.engine.model.MutableModelNode
import ch.senegal.engine.model.MutableModelTree
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
import org.xml.sax.helpers.DefaultHandler
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

    @Throws(SAXException::class)
    override fun startElement(uri: String, localName: String, qName: String, attr: Attributes) {
        val resolvedConcept = getConceptByXmlLocalName(localName) ?: return
        val newModelNode = currentMutableModelInstance.createAndAddMutableModelNode(resolvedConcept)
        Attribute.attributeList(attr).forEach { addAttribute(newModelNode, it) }
        this.currentMutableModelInstance = newModelNode
    }

    private fun addAttribute(mutableModelNode: MutableModelNode, attribute: Attribute) {
        val purposeFacetCombinedName = PurposeFacetCombinedName.of(CaseUtil.capitalize(attribute.localName))
        val resolvedFacet = mutableModelNode.resolvedConcept.getFacetByCombinedName(purposeFacetCombinedName)
            ?: this.fail("No facet found for name '${purposeFacetCombinedName.name}'.")

        val attributeValue = PlaceholderUtil.replacePlaceholders(attribute.value, placeholders)
        val facetValue = resolvedFacet.facet.facetType.facetValueFromString(attributeValue)
        mutableModelNode.addFacetValue(facet = resolvedFacet, facetValue = facetValue)
    }

    @Throws(SAXException::class)
    override fun endElement(uri: String, localName: String, qName: String) {
        if (!isConcept(localName)) return
        this.currentMutableModelInstance = currentMutableModelInstance.parentMutableModelInstance() ?: modelTree
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
