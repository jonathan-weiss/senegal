package ch.senegal.engine.plugin.tree

import ch.senegal.engine.plugin.Concept
import ch.senegal.engine.plugin.Purpose

class ConceptNode(
    val concept: Concept,
    val enclosedPurposes: Set<Purpose>,
    val enclosedConcepts: Set<ConceptNode>
    )
