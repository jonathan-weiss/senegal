package ch.cassiamon.engine.process.conceptgraph

import ch.cassiamon.api.process.datacollection.ConceptData
import ch.cassiamon.api.process.schema.SchemaAccess

object ConceptResolver {

    fun validateAndResolveConcepts(schema: SchemaAccess, conceptEntries: List<ConceptData>): ConceptGraph {
        // TODO Validate all concepts as single entries
        // TODO Find all referencing concepts, child concepts, parent concepts (?)
        // TODO Make a immutable ConceptNode

        return ConceptGraph(conceptEntries.map { createValidatedConceptNode(it) })
    }

    private fun createValidatedConceptNode(conceptData: ConceptData): ConceptGraph.ConceptNode {
        // TODO Add validation and resolve graph
        return ConceptGraph.ConceptNode(
            conceptName = conceptData.conceptName,
            conceptIdentifier = conceptData.conceptIdentifier,
            parentConceptIdentifier = conceptData.parentConceptIdentifier,
            facetValues = conceptData.allFacets(),
        )
    }
}
