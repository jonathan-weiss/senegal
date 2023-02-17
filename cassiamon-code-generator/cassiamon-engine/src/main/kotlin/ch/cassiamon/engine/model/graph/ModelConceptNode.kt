package ch.cassiamon.engine.model.graph

import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.model.ConceptIdentifier
import ch.cassiamon.pluginapi.model.ConceptNode
import ch.cassiamon.pluginapi.model.FacetValues

/**
 * A fully calculated concept node with facet values,
 * connected to other ModelConceptNode instances.
 */
class ModelConceptNode(
    override val conceptName: ConceptName,
    override val conceptIdentifier: ConceptIdentifier,
    val parentConceptIdentifier: ConceptIdentifier?,
    override val facetValues: FacetValuesImpl
): ConceptNode
