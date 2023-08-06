package ch.cassiamon.engine.process.conceptgraph

import ch.cassiamon.api.process.schema.ConceptIdentifier
import ch.cassiamon.api.process.schema.ConceptName
import ch.cassiamon.api.process.schema.FacetName

interface ConceptNode: SortedChildrenConceptNodesProvider {
    val sequenceNumber: Int
    val conceptName: ConceptName
    val conceptIdentifier: ConceptIdentifier
    val parentConceptNode: ConceptNode?
    val facetValues: Map<FacetName, Any?>
    override fun children(conceptName: ConceptName): List<ConceptNode>
    override fun children(conceptNames: Set<ConceptName>): List<ConceptNode>
}
