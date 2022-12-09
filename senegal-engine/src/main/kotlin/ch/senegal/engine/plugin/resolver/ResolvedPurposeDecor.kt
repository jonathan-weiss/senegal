package ch.senegal.engine.plugin.resolver

import ch.senegal.plugin.Concept
import ch.senegal.plugin.Purpose
import ch.senegal.plugin.PurposeDecor

class ResolvedPurposeDecor(
    val concept: Concept,
    val purpose: Purpose,
    val purposeDecor: PurposeDecor,
) {
    val purposeDecorName: String
    get() = "${purpose.purposeName.name}${purposeDecor.decorName.name}"
}
