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

    fun getPurposeDecorByCombinedName(purposeDecorName: String): PurposeDecor? {
        for (purpose in enclosedPurposes) {
            val foundEntry = purpose.purposeDecors
                .firstOrNull { "${purpose.purposeName.name}${it.decorName.name}" == purposeDecorName }
            if (foundEntry != null) {
                return foundEntry
            }
        }
        return null
    }
}
