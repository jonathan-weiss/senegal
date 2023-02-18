package ch.cassiamon.engine.model

import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.model.ConceptIdentifier
import ch.cassiamon.pluginapi.model.ConceptModelNode

class MutableConceptModelNode(override val conceptName: ConceptName,
                              override val conceptIdentifier: ConceptIdentifier,
                              override val facetValues: MutableConceptModelNodeFacetValues
) : ConceptModelNode {

    private var parent: ConceptModelNode? = null;
    private var children: Map<ConceptName, List<ConceptModelNode>> = emptyMap();
    fun assignHierarchicalTemplateNodes(parent: ConceptModelNode?, children: Map<ConceptName, List<ConceptModelNode>>) {

    }
    override fun parent(): ConceptModelNode? {
        return parent
    }

    override fun allChildren(): List<ConceptModelNode> {
        return children.values.flatten()
    }

    override fun children(conceptName: ConceptName): List<ConceptModelNode> {
        return children[conceptName] ?: emptyList()
    }

    override fun get(key: String): Any? {
        TODO("Not yet implemented")
    }


}
