package ch.senegal.engine.xml

import ch.senegal.engine.model.Decoration
import ch.senegal.engine.model.ModelInstance
import ch.senegal.engine.model.ModelNode
import ch.senegal.engine.model.ModelTree
import ch.senegal.engine.plugin.resolver.ResolvedConcept
import ch.senegal.engine.plugin.resolver.ResolvedPlugins
import ch.senegal.engine.util.CaseUtil
import ch.senegal.plugin.ConceptName
import org.xml.sax.Attributes
import org.xml.sax.SAXException
import org.xml.sax.helpers.DefaultHandler
import java.util.*


class SenegalSaxParserHandler(private val resolvedPlugins: ResolvedPlugins, private val modelTree: ModelTree) : DefaultHandler() {
    private var currentModelInstance: ModelInstance = modelTree

    @Throws(SAXException::class)
    override fun startElement(uri: String, localName: String, qName: String, attr: Attributes) {
        val resolvedConcept = getConceptByXmlLocalName(localName) ?: return
        val newModelNode = currentModelInstance.createAndAddModelNode(resolvedConcept)
        Attribute.attributeList(attr).forEach { addAttribute(newModelNode, it) }
        this.currentModelInstance = newModelNode
    }

    private fun addAttribute(modelNode: ModelNode, attribute: Attribute) {
        val purposeDecorName = CaseUtil.capitalize(attribute.localName)
        val purposeDecor = modelNode.resolvedConcept.getPurposeDecorByCombinedName(purposeDecorName)
            ?: this.fail("No purpose decor found for name '${purposeDecorName}'.")

        val decoration = Decoration(attribute.value)
        modelNode.addDecoration(purposeDecor = purposeDecor, decoration = decoration)
    }

    @Throws(SAXException::class)
    override fun endElement(uri: String, localName: String, qName: String) {
        if (!isConcept(localName)) return
        this.currentModelInstance = currentModelInstance.parentModelInstance() ?: modelTree
    }


    private fun isConcept(localName: String): Boolean {
        return getConceptByXmlLocalName(localName) != null
    }

    private fun fail(message: String): Nothing {
        throw SAXException(message)
    }

    private fun getConceptByXmlLocalName(localName: String): ResolvedConcept? {
        val potentialConceptName = ConceptName(CaseUtil.capitalize(localName))
        return resolvedPlugins.getConceptByConceptName(potentialConceptName)
    }
}
