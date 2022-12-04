package ch.senegal.engine.xml

import ch.senegal.engine.freemarker.nodetree.TemplateModelNode
import ch.senegal.engine.freemarker.nodetree.TemplateModelNodeBuilder
import ch.senegal.engine.plugin.ConceptName
import ch.senegal.engine.plugin.tree.PluginTree
import ch.senegal.engine.util.CaseUtil
import org.xml.sax.Attributes
import org.xml.sax.Locator
import org.xml.sax.SAXException
import org.xml.sax.helpers.DefaultHandler
import java.util.*
import kotlin.collections.ArrayDeque


class SenegalSaxParserHandler(private val pluginTree: PluginTree) : DefaultHandler() {
    private val rootNodes = mutableListOf<TemplateModelNode>()
    private var templateModelNodeBuilders: ArrayDeque<TemplateModelNodeBuilder> = ArrayDeque()


    @Throws(SAXException::class)
    override fun startElement(uri: String, localName: String, qName: String, attr: Attributes) {
        if(!isConcept(localName)) return
        val modelNodeBuilder = if(templateModelNodeBuilders.isEmpty()) {
            TemplateModelNodeBuilder.createNodeBuilder()
        } else {
            templateModelNodeBuilders.last().createAndAttachChildNodeBuilder()
        }



        val attributeList = Attribute.attributeList(attr)
        attributeList.forEach { modelNodeBuilder.addProperty(it.localName, it.value) }
        templateModelNodeBuilders.addLast(modelNodeBuilder)
    }

    private fun isConcept(localName: String): Boolean {
        return pluginTree.allConceptNodes
            .keys
            .map { CaseUtil.camelToDashCase(it.name) }
            .contains(localName)
    }

    @Throws(SAXException::class)
    override fun endElement(uri: String, localName: String, qName: String) {
        if(!isConcept(localName)) return
        val node = templateModelNodeBuilders.removeLast()
        if(templateModelNodeBuilders.isEmpty()) {
            rootNodes.add(node.build())
        }
    }

    fun getListOfRootTemplateModelNodes(): List<TemplateModelNode> {
        return rootNodes.toList()
    }
}
