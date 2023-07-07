package ch.cassiamon.engine.domain.process.conceptresolver

import ch.cassiamon.api.datacollection.ConceptData

object ConceptResolver {

    fun validateAndResolveConcepts(conceptEntries: List<ConceptData>): ResolvedConcepts {
        return ResolvedConcepts(conceptEntries.map { createValidatedConceptEntry(it) })
    }

    private fun createValidatedConceptEntry(conceptData: ConceptData): ResolvedConcepts.ConceptEntryData {
        // TODO Add validation and resolve graph
        return ResolvedConcepts.ConceptEntryData(
            conceptName = conceptData.conceptName,
            conceptIdentifier = conceptData.conceptIdentifier,
            parentConceptIdentifier = conceptData.parentConceptIdentifier,
            facetValues = conceptData.facets,
        )
    }
}
