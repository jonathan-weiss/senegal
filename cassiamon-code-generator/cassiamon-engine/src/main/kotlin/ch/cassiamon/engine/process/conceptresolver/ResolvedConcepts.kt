package ch.cassiamon.engine.process.conceptresolver

import ch.cassiamon.api.ConceptName
import ch.cassiamon.api.FacetName
import ch.cassiamon.api.ConceptIdentifier

class ResolvedConcepts(
    conceptData: List<ConceptEntryData>
) {
    private val concepts: List<ResolvedConcept> = conceptData.map { ResolvedConcept(it) }
    fun conceptsByConceptName(conceptName: ConceptName): List<ResolvedConcept> {
        return concepts
            .filter { it.conceptName == conceptName }
    }

    private fun childConceptsByConceptName(conceptName: ConceptName, parentConceptIdentifier: ConceptIdentifier,): List<ResolvedConcept> {
        return conceptsByConceptName(conceptName)
            .filter { it.parentConceptIdentifier != null && it.parentConceptIdentifier == parentConceptIdentifier }
    }

    inner class ResolvedConcept(conceptData: ConceptEntryData, ) {
        val conceptName: ConceptName = conceptData.conceptName
        val conceptIdentifier: ConceptIdentifier = conceptData.conceptIdentifier
        val parentConceptIdentifier: ConceptIdentifier? = conceptData.parentConceptIdentifier
        val facetValues: Map<FacetName, Any?> = conceptData.facetValues


        fun children(conceptName: ConceptName): List<ResolvedConcept> {
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
