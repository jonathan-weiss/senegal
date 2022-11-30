package ch.senegal.engine.plugin.tree

import ch.senegal.engine.plugin.Concept
import ch.senegal.engine.plugin.ConceptName
import ch.senegal.engine.plugin.Plugin
import ch.senegal.engine.plugin.Purpose

object PluginTreeCreator {

    fun createPluginTree(plugins: Set<Plugin>): PluginTree {
        val rootConcepts: Set<Concept> = plugins
            .filterIsInstance<Concept>()
            .filter { it.enclosingConceptName == null }
            .toSet()
        val childConceptsByParentConcept: Map<ConceptName, List<Concept>> = plugins
            .filterIsInstance<Concept>()
            .filter { it.enclosingConceptName != null }
            .groupBy { it.enclosingConceptName!! }

        val purposes: Map<ConceptName, List<Purpose>> = plugins
            .filterIsInstance<Purpose>()
            .groupBy { it.enclosingConceptName }

        val rootConceptNodes: Set<ConceptNode> = rootConcepts
            .map { createConceptNode(it, childConceptsByParentConcept, purposes) }
            .toSet()
        return PluginTree(rootConceptNodes)
    }

    private fun createConceptNode(concept: Concept,
                                  childConceptsByParentConcept: Map<ConceptName, List<Concept>>,
                                  purposes: Map<ConceptName, List<Purpose>>): ConceptNode {
        return ConceptNode(
            concept = concept,
            enclosedPurposes = purposes[concept.conceptName]?.toSet() ?: emptySet(),
            enclosedConcepts = childConceptsByParentConcept[concept.conceptName]
                ?.map { createConceptNode(it, childConceptsByParentConcept, purposes) }
                ?.toSet() ?: emptySet()
        )
    }
}
