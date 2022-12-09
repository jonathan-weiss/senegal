package ch.senegal.engine.model

import ch.senegal.plugin.PurposeDecor
import ch.senegal.engine.plugin.resolver.ResolvedConcept

class ModelNode(val resolvedConcept: ResolvedConcept,
                val parentModelInstance: ModelInstance,
): ModelInstance() {

    private val modelDecorations: MutableMap<PurposeDecor, Decoration> = mutableMapOf()

    override fun parentModelInstance(): ModelInstance {
        return parentModelInstance
    }

    fun addDecoration(purposeDecor: PurposeDecor, decoration: Decoration) {
        modelDecorations[purposeDecor] = decoration
    }

//    fun getDecoration(purposeDecorName: PurposeDecorName): Decoration? {
//        return modelDecorations[purposeDecorName]
//    }
}
