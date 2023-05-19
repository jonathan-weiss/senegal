package ch.cassiamon.engine.model

import ch.cassiamon.api.ConceptName
import ch.cassiamon.api.model.ConceptModelNode
import ch.cassiamon.api.model.ConceptModelNodeTemplateFacetValues

class MaterializingConceptModelNode(
    private val conceptModelNode: ConceptModelNode
) : ConceptModelNode by conceptModelNode {

    private val materializingConceptModelNodeFacetValues = MaterializingConceptModelNodeTemplateFacetValues(
        conceptModelNode = conceptModelNode,
    )
    private var isParentMaterialized: Boolean = false
    private var materializedParent: ConceptModelNode? = null;

    private var isChildrenMaterialized: Boolean = false
    private var materializedChildren: Map<ConceptName, List<ConceptModelNode>> = emptyMap();
    override fun parent(): ConceptModelNode? {
        fetchParentIfNecessary()
        return materializedParent
    }

    override fun allChildren(): List<ConceptModelNode> {
        fetchChildrenIfNecessary()
        return materializedChildren.values.flatten()
    }

    override fun children(conceptName: ConceptName): List<ConceptModelNode> {
        fetchChildrenIfNecessary()
        return materializedChildren[conceptName] ?: emptyList()
    }

    private fun fetchChildrenIfNecessary() {
        if(!isChildrenMaterialized) {
            materializedChildren = conceptModelNode.allChildren().groupBy { it.conceptName }
            isChildrenMaterialized = true
        }
    }

    private fun fetchParentIfNecessary() {
        if(!isParentMaterialized) {
            materializedParent = conceptModelNode.parent()
            isParentMaterialized = true
        }
    }


    override val templateFacetValues: ConceptModelNodeTemplateFacetValues
        get() = materializingConceptModelNodeFacetValues

}
