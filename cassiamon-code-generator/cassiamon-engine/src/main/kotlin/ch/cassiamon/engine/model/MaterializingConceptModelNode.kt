package ch.cassiamon.engine.model

import ch.cassiamon.engine.model.types.FacetValue
import ch.cassiamon.engine.schema.types.Schema
import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName
import ch.cassiamon.pluginapi.model.ConceptIdentifier
import ch.cassiamon.pluginapi.model.ConceptModelNode
import ch.cassiamon.pluginapi.model.ConceptModelNodeFacetValues

class MaterializingConceptModelNode(
    schema: Schema,
    infiniteLoopDetector: InfiniteLoopDetector,
    nodePool: ConceptModelNodePool,
    override val conceptName: ConceptName,
    override val conceptIdentifier: ConceptIdentifier,
    manualFacetValues: Map<FacetName, FacetValue>,
) : ConceptModelNode {

    private val materializingConceptModelNodeFacetValues = MaterializingConceptModelNodeFacetValues(
        schema = schema,
        infiniteLoopDetector = infiniteLoopDetector,
        nodePool = nodePool,
        conceptName = conceptName,
        conceptIdentifier = conceptIdentifier,
        manualFacetValues = manualFacetValues
    )
    private var materializedParent: ConceptModelNode? = null;
    private var materializedChildren: Map<ConceptName, List<ConceptModelNode>> = emptyMap();
    override fun parent(): ConceptModelNode? {
        return materializedParent // TODO ask nodePool and materialize the parent
    }

    override fun allChildren(): List<ConceptModelNode> {
        return materializedChildren.values.flatten() // TODO ask nodePool and materialize the parent
    }

    override fun children(conceptName: ConceptName): List<ConceptModelNode> {
        return materializedChildren[conceptName] ?: emptyList() // TODO ask nodePool and materialize the parent
    }

    override val facetValues: ConceptModelNodeFacetValues
        get() = materializingConceptModelNodeFacetValues

    override fun get(key: String): Any? {
        TODO("Not yet implemented")
    }


}
