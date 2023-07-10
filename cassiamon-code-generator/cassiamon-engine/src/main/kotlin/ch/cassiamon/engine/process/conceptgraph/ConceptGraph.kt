package ch.cassiamon.engine.process.conceptgraph

import ch.cassiamon.api.process.schema.ConceptName
import ch.cassiamon.api.process.schema.ConceptIdentifier
import kotlin.jvm.Throws

class ConceptGraph(
    private val concepts: Map<ConceptIdentifier, ConceptNode>,
) {
    private val rootConceptsByConceptName: Map<ConceptName, List<ConceptNode>> = concepts.values
        .filter { it.parentConceptNode == null }
        .groupBy { it.conceptName }
    fun rootConceptsByConceptName(conceptName: ConceptName): List<ConceptNode> {
        return rootConceptsByConceptName[conceptName] ?: emptyList()
    }

    @Throws(NoSuchElementException::class)
    fun conceptByConceptIdentifier(conceptIdentifier: ConceptIdentifier): ConceptNode {
        return concepts[conceptIdentifier] ?: throw NoSuchElementException("No ConceptNode with id '${conceptIdentifier.code}'.")
    }

}
