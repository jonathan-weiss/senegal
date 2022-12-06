package ch.senegal.engine.xml

import ch.senegal.engine.model.Decoration
import ch.senegal.engine.model.ModelInstance
import ch.senegal.engine.model.ModelNode
import ch.senegal.engine.model.ModelTree
import ch.senegal.plugin.PurposeDecorName
import ch.senegal.engine.plugin.tree.ConceptNode
import ch.senegal.engine.plugin.tree.PluginTree
import ch.senegal.engine.util.CaseUtil
import org.xml.sax.Attributes
import org.xml.sax.SAXException
import org.xml.sax.helpers.DefaultHandler
import java.util.*


class SenegalSaxParserHandler(private val pluginTree: PluginTree, private val modelTree: ModelTree) : DefaultHandler() {
    private var currentModelInstance: ModelInstance = modelTree

    @Throws(SAXException::class)
    override fun startElement(uri: String, localName: String, qName: String, attr: Attributes) {
        val conceptNode = getConceptByName(localName) ?: return
        val newModelNode = currentModelInstance.createAndAddModelNode(conceptNode)
        Attribute.attributeList(attr).forEach { addAttribute(newModelNode, it) }
        this.currentModelInstance = newModelNode
    }

    private fun addAttribute(modelNode: ModelNode, attribute: Attribute) {
        // TODO how to transform attribute name to purposeDecorName (dash to camelCase)?
        val purposeDecorName = PurposeDecorName(attribute.localName)
        val purposeDecor = modelNode.conceptNode.getPurposeByName(purposeDecorName)
            ?: this.fail("No purpose decor found for name ${purposeDecorName.name}")

        val decoration = Decoration(attribute.value)
        modelNode.addDecoration(purposeDecor = purposeDecor, decoration = decoration)
    }

    @Throws(SAXException::class)
    override fun endElement(uri: String, localName: String, qName: String) {
        if(!isConcept(localName)) return
        this.currentModelInstance = currentModelInstance.parentModelInstance() ?: modelTree
    }


    private fun isConcept(localName: String): Boolean {
        return getConceptByName(localName) != null
    }

    private fun fail(message: String): Nothing {
        throw SAXException(message)
    }

    private fun getConceptByName(localName: String): ConceptNode? {
        // TODO get concept name from xml later, when it is written in schema
//        val potentialConceptName = ConceptName(localName)
//        return pluginTree.allConceptNodes[potentialConceptName]
        return pluginTree.allConceptNodes
            .values.firstOrNull { CaseUtil.camelToDashCase(it.concept.conceptName.name) == localName }
    }
}
