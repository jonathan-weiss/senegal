package ch.senegal.pluginexample

import ch.senegal.plugin.*


object KotlinModelPurposePlugin : Purpose {
    override val purposeName: PurposeName = PurposeName("KotlinModel")
    override val purposeDecors: Set<PurposeDecor> = setOf(KotlinModelClassnameDecor, KotlinModelPackageDecor)
}

object KotlinModelClassnameDecor : PurposeDecor {
    override val decorName: DecorName = DecorName("ClassName")
    override val enclosingConceptName = EntityConceptPlugin.conceptName
    override val decorType: DecorType = TextDecorType
}

object KotlinModelPackageDecor : PurposeDecor {
    override val decorName: DecorName = DecorName("Package")
    override val enclosingConceptName = EntityConceptPlugin.conceptName
    override val decorType: DecorType = TextDecorType
}

object KotlinFieldPurposePlugin : Purpose {
    override val purposeName: PurposeName = PurposeName("KotlinField")
    override val purposeDecors: Set<PurposeDecor> = setOf(KotlinFieldNameDecor, KotlinFieldTypeDecor)
}

object KotlinFieldNameDecor : PurposeDecor {
    override val decorName: DecorName = DecorName("Name")
    override val enclosingConceptName = EntityAttributeConceptPlugin.conceptName
    override val decorType: DecorType = TextDecorType
}

object KotlinFieldTypeDecor : PurposeDecor {
    override val decorName: DecorName = DecorName("Type")
    override val enclosingConceptName = EntityAttributeConceptPlugin.conceptName
    override val decorType: DecorType = EnumerationDecorType(
        listOf(
            DecorTypeEnumerationValue("kotlin.String"),
            DecorTypeEnumerationValue("kotlin.Int"),
            DecorTypeEnumerationValue("kotlin.Boolean"),
        )
    )
}