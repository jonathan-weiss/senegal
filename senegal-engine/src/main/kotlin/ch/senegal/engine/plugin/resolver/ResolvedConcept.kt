package ch.senegal.engine.plugin.resolver

import ch.senegal.plugin.Concept
import ch.senegal.plugin.Purpose
import ch.senegal.plugin.PurposeDecor

class ResolvedConcept(
    val concept: Concept,
    val enclosedPurposes: Set<Purpose>,
    val enclosedPurposeDecors: List<ResolvedPurposeDecor>,
    val enclosedConcepts: Set<ResolvedConcept>
) {

    fun getPurposeDecorByCombinedName(purposeDecorName: String): ResolvedPurposeDecor? {
        return enclosedPurposeDecors.firstOrNull { it.purposeDecorName == purposeDecorName }
    }
}
