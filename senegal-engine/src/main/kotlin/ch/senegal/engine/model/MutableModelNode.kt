package ch.senegal.engine.model

import ch.senegal.engine.plugin.resolver.ResolvedConcept
import ch.senegal.engine.plugin.resolver.ResolvedPurposeDecor

class MutableModelNode(val resolvedConcept: ResolvedConcept,
                       private val parentMutableModelInstance: MutableModelInstance,
): MutableModelInstance() {

    val modelDecorations: MutableMap<ResolvedPurposeDecor, Decoration> = mutableMapOf()

    override fun parentModelInstance(): MutableModelInstance {
        return parentMutableModelInstance
    }

    fun addDecoration(purposeDecor: ResolvedPurposeDecor, decoration: Decoration) {
        modelDecorations[purposeDecor] = decoration
    }

//    fun getDecoration(purposeDecorName: PurposeDecorName): Decoration? {
//        return modelDecorations[purposeDecorName]
//    }
}
