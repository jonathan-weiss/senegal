package ch.senegal.engine.model

import ch.senegal.plugin.PurposeDecor
import ch.senegal.engine.plugin.resolver.ResolvedConcept
import ch.senegal.engine.plugin.resolver.ResolvedPurposeDecor

class ModelNode(val resolvedConcept: ResolvedConcept,
                val parentModelInstance: ModelInstance,
): ModelInstance() {

    val modelDecorations: MutableMap<ResolvedPurposeDecor, Decoration> = mutableMapOf()

    override fun parentModelInstance(): ModelInstance {
        return parentModelInstance
    }

    fun addDecoration(purposeDecor: ResolvedPurposeDecor, decoration: Decoration) {
        modelDecorations[purposeDecor] = decoration
    }

//    fun getDecoration(purposeDecorName: PurposeDecorName): Decoration? {
//        return modelDecorations[purposeDecorName]
//    }
}
