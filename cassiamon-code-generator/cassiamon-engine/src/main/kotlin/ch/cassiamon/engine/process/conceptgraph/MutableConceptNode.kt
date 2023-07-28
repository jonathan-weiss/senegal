package ch.cassiamon.engine.process.conceptgraph

import ch.cassiamon.api.process.schema.ConceptIdentifier
import ch.cassiamon.api.process.schema.ConceptName
import ch.cassiamon.api.process.schema.FacetName

class MutableConceptNode(
    override val conceptName: ConceptName,
    override val conceptIdentifier: ConceptIdentifier,
    override var parentConceptNode: MutableConceptNode? = null,
    var childrenConceptNodes: Map<ConceptName, List<MutableConceptNode>> = emptyMap(),
    override var facetValues: MutableMap<FacetName, Any?> = mutableMapOf(),
): ConceptNode {
    override fun children(conceptName: ConceptName): List<ConceptNode> {
        return childrenConceptNodes[conceptName] ?: emptyList()
    }
}