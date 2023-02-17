package ch.cassiamon.engine.model.graph

import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.model.ConceptIdentifier
import ch.cassiamon.pluginapi.model.ConceptNode
import ch.cassiamon.pluginapi.model.FacetValues

/**
 * A fully calculated concept node with facet values,
 * connected to other ModelConceptNode instances.
 */
class ModelConceptNode: ConceptNode {
    override val conceptName: ConceptName
        get() = TODO("Not yet implemented")
    override val conceptIdentifier: ConceptIdentifier
        get() = TODO("Not yet implemented")
    override val facetValues: FacetValues
        get() = TODO("Not yet implemented")
}
