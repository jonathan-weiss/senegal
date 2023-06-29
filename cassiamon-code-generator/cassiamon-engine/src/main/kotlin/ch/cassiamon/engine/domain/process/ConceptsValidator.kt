package ch.cassiamon.engine.domain.process

import ch.cassiamon.api.registration.ConceptEntries
import ch.cassiamon.api.registration.ConceptEntry

object ConceptsValidator {

    fun validateAndResolveConcepts(conceptEntries: ConceptEntries): Concepts {
        return Concepts(conceptEntries.concepts.map { createValidatedConceptEntry(it) })
    }

    private fun createValidatedConceptEntry(conceptEntry: ConceptEntry): Concepts.ConceptEntryData {
        // TODO Add validation
        return Concepts.ConceptEntryData(
            conceptName = conceptEntry.conceptName,
            conceptIdentifier = conceptEntry.conceptIdentifier,
            parentConceptIdentifier = conceptEntry.parentConceptIdentifier,
            facetValues = conceptEntry.facetValues,
        )
    }
}
