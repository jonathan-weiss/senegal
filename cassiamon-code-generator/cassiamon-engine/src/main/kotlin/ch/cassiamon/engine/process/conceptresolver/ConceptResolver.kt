package ch.cassiamon.engine.process.conceptresolver

import ch.cassiamon.api.process.datacollection.ConceptData

object ConceptResolver {

    fun validateAndResolveConcepts(conceptEntries: List<ConceptData>): ConceptGraph {
        return ConceptGraph(conceptEntries.map { createValidatedConceptEntry(it) })
    }

    private fun createValidatedConceptEntry(conceptData: ConceptData): ConceptGraph.ConceptNode {
        // TODO Add validation and resolve graph
        return ConceptGraph.ConceptNode(
            conceptName = conceptData.conceptName,
            conceptIdentifier = conceptData.conceptIdentifier,
            parentConceptIdentifier = conceptData.parentConceptIdentifier,
            facetValues = conceptData.allFacets(),
        )
    }
}
