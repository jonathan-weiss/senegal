package ch.senegal.pluginexample

import ch.senegal.plugin.*

object EntityPurposePlugin : Purpose {
    override val purposeName: PurposeName = PurposeName("Entity")
    override val purposeDecors: Set<PurposeDecor> = setOf(EntityNameDecor)
}

object EntityNameDecor : PurposeDecor {
    override val decorName: DecorName = DecorName("Name")
    override val enclosingConceptName: ConceptName = EntityConceptPlugin.conceptName
    override val decorType: DecorType = TextDecorType
}

