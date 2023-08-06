package ch.cassiamon.engine.process.conceptgraph

import ch.cassiamon.api.process.schema.ConceptName

interface SortedChildrenConceptNodesProvider {
    fun children(conceptName: ConceptName): List<ConceptNode>
    fun children(conceptNames: Set<ConceptName>): List<ConceptNode>
}
