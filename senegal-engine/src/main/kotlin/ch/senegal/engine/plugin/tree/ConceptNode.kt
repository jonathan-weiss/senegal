package ch.senegal.engine.plugin.tree

import ch.senegal.plugin.Concept
import ch.senegal.plugin.Purpose
import ch.senegal.plugin.PurposeDecor
import ch.senegal.plugin.PurposeName

class ConceptNode(
    val concept: Concept,
    val enclosedPurposes: Set<Purpose>,
    val enclosedPurposeDecors: List<PurposeDecorEntry>,
    val enclosedConcepts: Set<ConceptNode>
    ) {

    fun getPurposeDecorByCombinedName(purposeDecorName: String): PurposeDecor? {
        for (purpose in enclosedPurposes) {
            val foundEntry = purpose.purposeDecors
                .firstOrNull { "${purpose.purposeName.name}${it.purposeDecorName.name}" == purposeDecorName }
            if(foundEntry != null) {
                return foundEntry
            }
        }
        return null
    }
}
