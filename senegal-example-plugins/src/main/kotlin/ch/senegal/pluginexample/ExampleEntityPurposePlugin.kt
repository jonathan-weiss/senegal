package ch.senegal.pluginexample

import ch.senegal.plugin.*

object EntityPurposePlugin : Purpose {
    override val purposeName: PurposeName = PurposeName("Entity")
    override val enclosingConceptName: ConceptName = EntityConceptPlugin.conceptName
    override val purposeDecors: Set<PurposeDecor> = setOf(EntityNameDecor)
}

object EntityNameDecor : PurposeDecor {
    override val purposeDecorName: PurposeDecorName = PurposeDecorName("Name")
    override val purposeDecorType: DecorType = TextDecorType
}

