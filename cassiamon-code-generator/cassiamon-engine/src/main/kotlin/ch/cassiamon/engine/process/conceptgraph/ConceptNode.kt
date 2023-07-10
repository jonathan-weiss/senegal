package ch.cassiamon.engine.process.conceptgraph

import ch.cassiamon.api.process.schema.ConceptIdentifier
import ch.cassiamon.api.process.schema.ConceptName
import ch.cassiamon.api.process.schema.FacetName

interface ConceptNode {
    val conceptName: ConceptName
    val conceptIdentifier: ConceptIdentifier
    val parentConceptNode: ConceptNode?
    val facetValues: Map<FacetName, Any?>
    fun children(conceptName: ConceptName): List<ConceptNode>
}
