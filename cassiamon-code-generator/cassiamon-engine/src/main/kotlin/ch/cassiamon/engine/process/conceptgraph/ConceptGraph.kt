package ch.cassiamon.engine.process.conceptgraph

import ch.cassiamon.api.process.schema.ConceptName
import ch.cassiamon.api.process.schema.ConceptIdentifier
import kotlin.jvm.Throws

class ConceptGraph(
    private val concepts: Map<ConceptIdentifier, ConceptNode>,
): SortedChildrenConceptNodesProvider {
    private val rootConceptsByConceptName: Map<ConceptName, List<ConceptNode>> = concepts.values
        .filter { it.parentConceptNode == null }
        .groupBy { it.conceptName }

    @Throws(NoSuchElementException::class)
    fun conceptByConceptIdentifier(conceptIdentifier: ConceptIdentifier): ConceptNode {
        return concepts[conceptIdentifier] ?: throw NoSuchElementException("No ConceptNode with id '${conceptIdentifier.name}'.")
    }

    override fun children(conceptName: ConceptName): List<ConceptNode> {
        return children(setOf(conceptName))
    }

    override fun children(conceptNames: Set<ConceptName>): List<ConceptNode> {
        return conceptNames
            .flatMap { conceptName -> rootConceptsByConceptName(conceptName) }
            .sortedBy { conceptNode -> conceptNode.sequenceNumber }
    }

    private fun rootConceptsByConceptName(conceptName: ConceptName): List<ConceptNode> {
        return rootConceptsByConceptName[conceptName] ?: emptyList()
    }

}
