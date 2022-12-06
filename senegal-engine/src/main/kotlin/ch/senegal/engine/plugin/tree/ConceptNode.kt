package ch.senegal.engine.plugin.tree

import ch.senegal.plugin.Concept
import ch.senegal.plugin.Purpose
import ch.senegal.plugin.PurposeDecor
import ch.senegal.plugin.PurposeDecorName

class ConceptNode(
    val concept: Concept,
    val enclosedPurposes: Set<Purpose>,
    val enclosedConcepts: Set<ConceptNode>
    ) {

    fun getPurposeByName(purposeDecorName: PurposeDecorName): PurposeDecor? {
        return enclosedPurposes.flatMap { it.purposeDecors }.firstOrNull { it.purposeDecorName == purposeDecorName }
    }
}
