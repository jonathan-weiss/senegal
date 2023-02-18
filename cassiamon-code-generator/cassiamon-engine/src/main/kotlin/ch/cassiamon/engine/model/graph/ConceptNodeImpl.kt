package ch.cassiamon.engine.model.graph

import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.model.ConceptIdentifier
import ch.cassiamon.pluginapi.model.ConceptNode
import ch.cassiamon.pluginapi.model.FacetValues

class ConceptNodeImpl(
    override val conceptName: ConceptName,
    override val conceptIdentifier: ConceptIdentifier,
    override val facetValues: FacetValues
) : ConceptNode {
}
