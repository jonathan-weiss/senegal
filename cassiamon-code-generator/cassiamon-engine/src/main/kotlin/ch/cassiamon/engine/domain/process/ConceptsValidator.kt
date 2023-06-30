package ch.cassiamon.engine.domain.process

import ch.cassiamon.api.registration.ConceptData
import ch.cassiamon.api.registration.ConceptEntry

object ConceptsValidator {

    fun validateAndResolveConcepts(conceptEntries: List<ConceptData>): Concepts {
        return Concepts(conceptEntries.map { createValidatedConceptEntry(it) })
    }

    private fun createValidatedConceptEntry(conceptData: ConceptData): Concepts.ConceptEntryData {
        // TODO Add validation and resolve graph
        return Concepts.ConceptEntryData(
            conceptName = conceptData.conceptName,
            conceptIdentifier = conceptData.conceptIdentifier,
            parentConceptIdentifier = conceptData.parentConceptIdentifier,
            facetValues = conceptData.facets,
        )
    }
}
