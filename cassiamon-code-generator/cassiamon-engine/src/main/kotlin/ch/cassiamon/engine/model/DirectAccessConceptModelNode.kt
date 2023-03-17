package ch.cassiamon.engine.model

import ch.cassiamon.engine.schema.Schema
import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.model.*

class DirectAccessConceptModelNode(
    schema: Schema,
    infiniteLoopDetector: InfiniteLoopDetector,
    nodePool: ConceptModelNodePool,
    override val conceptName: ConceptName,
    override val conceptIdentifier: ConceptIdentifier,
    inputFacetValues: InputFacetValueAccess,
) : ConceptModelNode {

    private val directAccessConceptModelNodeFacetValues = DirectAccessConceptModelNodeTemplateFacetValues(
        schema = schema,
        infiniteLoopDetector = infiniteLoopDetector,
        nodePool = nodePool,
        conceptName = conceptName,
        conceptIdentifier = conceptIdentifier,
        manualFacetValues = inputFacetValues
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

    override val templateFacetValues: ConceptModelNodeTemplateFacetValues
        get() = directAccessConceptModelNodeFacetValues

    override fun get(key: String): Any? {
        TODO("Not yet implemented")
    }


}
