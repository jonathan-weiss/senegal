package ch.senegal.engine.plugin.tree

import ch.senegal.engine.plugin.Concept
import ch.senegal.engine.plugin.Purpose
import ch.senegal.engine.plugin.PurposeDecor
import ch.senegal.engine.plugin.PurposeDecorName

class ConceptNode(
    val concept: Concept,
    val enclosedPurposes: Set<Purpose>,
    val enclosedConcepts: Set<ConceptNode>
    ) {

    fun getPurposeByName(purposeDecorName: PurposeDecorName): PurposeDecor? {
        return enclosedPurposes.flatMap { it.purposeDecors }.firstOrNull { it.purposeDecorName == purposeDecorName }
    }
}
