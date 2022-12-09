package ch.senegal.pluginexample

import ch.senegal.plugin.*

object EntityAttributePurposePlugin : Purpose {
    override val purposeName: PurposeName = PurposeName("EntityAttribute")
    override val purposeDecors: Set<PurposeDecor> = setOf(EntityAttributeNameDecor, EntityAttributeTypeDecor)
}

object EntityAttributeTypeDecor : PurposeDecor {
    override val decorName: DecorName = DecorName("Type")
    override val enclosingConceptName: ConceptName = EntityAttributeConceptPlugin.conceptName
    override val decorType: DecorType = EnumerationDecorType(
        listOf(
            DecorTypeEnumerationValue("TEXT"),
            DecorTypeEnumerationValue("NUMBER"),
            DecorTypeEnumerationValue("BOOLEAN")
        )
    )

}

object EntityAttributeNameDecor : PurposeDecor {
    override val decorName: DecorName = DecorName("Name")
    override val enclosingConceptName: ConceptName = EntityAttributeConceptPlugin.conceptName
    override val decorType: DecorType = TextDecorType
}
