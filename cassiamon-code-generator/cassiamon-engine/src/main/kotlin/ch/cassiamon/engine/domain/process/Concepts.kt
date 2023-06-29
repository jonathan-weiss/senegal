package ch.cassiamon.engine.domain.process

import ch.cassiamon.api.ConceptName
import ch.cassiamon.api.FacetName
import ch.cassiamon.api.model.ConceptIdentifier

class Concepts(
    conceptData: List<ConceptEntryData>
) {
    private val concepts: List<ConceptEntry> = conceptData.map { ConceptEntry(it) }
    fun conceptsByConceptName(conceptName: ConceptName): List<ConceptEntry> {
        return concepts
            .filter { it.conceptName == conceptName }
    }

    private fun childConceptsByConceptName(conceptName: ConceptName, parentConceptIdentifier: ConceptIdentifier,): List<ConceptEntry> {
        return conceptsByConceptName(conceptName)
            .filter { it.parentConceptIdentifier != null && it.parentConceptIdentifier == parentConceptIdentifier }
    }

    inner class ConceptEntry(conceptData: ConceptEntryData, ) {
        val conceptName: ConceptName = conceptData.conceptName
        val conceptIdentifier: ConceptIdentifier = conceptData.conceptIdentifier
        val parentConceptIdentifier: ConceptIdentifier? = conceptData.parentConceptIdentifier
        val facetValues: Map<FacetName, Any?> = conceptData.facetValues


        fun children(conceptName: ConceptName): List<ConceptEntry> {
            return childConceptsByConceptName(conceptName, conceptIdentifier)
        }
    }

    data class ConceptEntryData(
        val conceptName: ConceptName,
        val conceptIdentifier: ConceptIdentifier,
        val parentConceptIdentifier: ConceptIdentifier?,
        val facetValues: Map<FacetName, Any?>,
    )

}
