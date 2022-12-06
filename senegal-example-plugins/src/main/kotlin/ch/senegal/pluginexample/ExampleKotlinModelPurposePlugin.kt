package ch.senegal.pluginexample

import ch.senegal.plugin.*


object KotlinModelPurposePlugin : Purpose {
    override val purposeName: PurposeName = PurposeName("KotlinModel")
    override val enclosingConceptName = EntityConceptPlugin.conceptName
    override val purposeDecors: Set<PurposeDecor> = setOf(KotlinModelClassnameDecor, KotlinModelPackageDecor)
}

object KotlinModelClassnameDecor : PurposeDecor {
    override val purposeDecorName: PurposeDecorName = PurposeDecorName("ClassName")
    override val purposeDecorType: DecorType = TextDecorType
}

object KotlinModelPackageDecor : PurposeDecor {
    override val purposeDecorName: PurposeDecorName = PurposeDecorName("Package")
    override val purposeDecorType: DecorType = TextDecorType
}

object KotlinFieldPurposePlugin : Purpose {
    override val purposeName: PurposeName = PurposeName("KotlinField")
    override val enclosingConceptName = EntityAttributeConceptPlugin.conceptName
    override val purposeDecors: Set<PurposeDecor> = setOf(KotlinFieldNameDecor, KotlinFieldTypeDecor)
}

object KotlinFieldNameDecor : PurposeDecor {
    override val purposeDecorName: PurposeDecorName = PurposeDecorName("Name")
    override val purposeDecorType: DecorType = TextDecorType
}

object KotlinFieldTypeDecor : PurposeDecor {
    override val purposeDecorName: PurposeDecorName = PurposeDecorName("Type")
    override val purposeDecorType: DecorType = EnumerationDecorType(
        listOf(
            DecorTypeEnumerationValue("kotlin.String"),
            DecorTypeEnumerationValue("kotlin.Int"),
            DecorTypeEnumerationValue("kotlin.Boolean"),
        )
    )
}
