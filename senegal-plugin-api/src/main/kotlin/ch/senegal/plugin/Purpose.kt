package ch.senegal.plugin

interface Purpose: Plugin {

    val purposeName: PurposeName

    val purposeDecors: Set<PurposeDecor>

    fun createTemplateTargets(/* pass the model */): Set<TemplateTarget> = emptySet()
}
