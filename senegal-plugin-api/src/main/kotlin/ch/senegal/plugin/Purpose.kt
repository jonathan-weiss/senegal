package ch.senegal.plugin

import ch.senegal.plugin.model.ModelNode

interface Purpose: Plugin {

    val purposeName: PurposeName

    val purposeDecors: Set<PurposeDecor>

    fun createTemplateTargets(modelNode: ModelNode): Set<TemplateTarget> = emptySet()
}
