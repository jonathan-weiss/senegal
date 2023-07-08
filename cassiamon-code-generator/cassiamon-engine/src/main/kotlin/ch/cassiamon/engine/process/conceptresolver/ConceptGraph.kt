package ch.cassiamon.engine.process.conceptresolver

import ch.cassiamon.api.process.schema.ConceptName
import ch.cassiamon.api.process.schema.FacetName
import ch.cassiamon.api.process.schema.ConceptIdentifier

class ConceptGraph(
    conceptNodes: List<ConceptNode>
) {
    private val concepts: List<ResolvedConcept> = conceptNodes.map { ResolvedConcept(it) }
    fun conceptsByConceptName(conceptName: ConceptName): List<ResolvedConcept> {
        return concepts
            .filter { it.conceptName == conceptName }
    }

    private fun childConceptsByConceptName(conceptName: ConceptName, parentConceptIdentifier: ConceptIdentifier,): List<ResolvedConcept> {
        return conceptsByConceptName(conceptName)
            .filter { it.parentConceptIdentifier != null && it.parentConceptIdentifier == parentConceptIdentifier }
    }

    inner class ResolvedConcept(conceptData: ConceptNode, ) {
        val conceptName: ConceptName = conceptData.conceptName
        val conceptIdentifier: ConceptIdentifier = conceptData.conceptIdentifier
        val parentConceptIdentifier: ConceptIdentifier? = conceptData.parentConceptIdentifier
        val facetValues: Map<FacetName, Any?> = conceptData.facetValues


        fun children(conceptName: ConceptName): List<ResolvedConcept> {
            return childConceptsByConceptName(conceptName, conceptIdentifier)
        }
    }

    data class ConceptNode(
        val conceptName: ConceptName,
        val conceptIdentifier: ConceptIdentifier,
        val parentConceptIdentifier: ConceptIdentifier?,
        val facetValues: Map<FacetName, Any?>,
    )

}
