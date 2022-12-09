package ch.senegal.pluginexample

import ch.senegal.plugin.*

object EntityAttributePurposePlugin : Purpose {
    override val purposeName: PurposeName = PurposeName("EntityAttribute")
    override val purposeDecors: Set<PurposeDecor> = setOf(EntityAttributeNameDecor, EntityAttributeTypeDecor)
}

object EntityAttributeTypeDecor : PurposeDecor {
    override val purposeDecorName: PurposeDecorName = PurposeDecorName("Type")
    override val enclosingConceptName: ConceptName = EntityAttributeConceptPlugin.conceptName
    override val purposeDecorType: DecorType = EnumerationDecorType(
        listOf(
            DecorTypeEnumerationValue("TEXT"),
            DecorTypeEnumerationValue("NUMBER"),
            DecorTypeEnumerationValue("BOOLEAN")
        )
    )

}

object EntityAttributeNameDecor : PurposeDecor {
    override val purposeDecorName: PurposeDecorName = PurposeDecorName("Name")
    override val enclosingConceptName: ConceptName = EntityAttributeConceptPlugin.conceptName
    override val purposeDecorType: DecorType = TextDecorType
}
