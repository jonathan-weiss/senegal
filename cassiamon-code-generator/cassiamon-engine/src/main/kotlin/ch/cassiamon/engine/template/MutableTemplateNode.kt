package ch.cassiamon.engine.template

import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.model.ConceptIdentifier
import ch.cassiamon.pluginapi.template.TemplateNode

class MutableTemplateNode(override val conceptName: ConceptName,
                          override val conceptIdentifier: ConceptIdentifier,
                          override val facetValues: MutableTemplateNodeFacetValues) : TemplateNode {

    private var parent: TemplateNode? = null;
    private var children: Map<ConceptName, List<TemplateNode>> = emptyMap();
    fun assignHierarchicalTemplateNodes(parent: TemplateNode?, children: Map<ConceptName, List<TemplateNode>>) {

    }
    override fun parent(): TemplateNode? {
        return parent
    }

    override fun allChildren(): List<TemplateNode> {
        return children.values.flatten()
    }

    override fun children(conceptName: ConceptName): List<TemplateNode> {
        return children[conceptName] ?: emptyList()
    }

    override fun get(key: String): Any? {
        TODO("Not yet implemented")
    }


}
