package ch.senegal.pluginexample

import ch.senegal.plugin.*


object KotlinModelPurposePlugin : Purpose {
    override val purposeName: PurposeName = PurposeName("kotlinModel")
    override val enclosingConceptName = EntityConceptPlugin.conceptName
    override val purposeDecors: Set<PurposeDecor> = setOf(KotlinModelClassnameDecor, KotlinModelPackageDecor)
}

object KotlinModelClassnameDecor : PurposeDecor {
    override val purposeDecorName: PurposeDecorName = PurposeDecorName("className")
    override val purposeDecorType: DecorType = TextDecorType
}

object KotlinModelPackageDecor : PurposeDecor {
    override val purposeDecorName: PurposeDecorName = PurposeDecorName("package")
    override val purposeDecorType: DecorType = TextDecorType
}

object KotlinFieldPurposePlugin : Purpose {
    override val purposeName: PurposeName = PurposeName("kotlinField")
    override val enclosingConceptName = EntityAttributeConceptPlugin.conceptName
    override val purposeDecors: Set<PurposeDecor> = setOf(KotlinFieldNameDecor, KotlinFieldTypeDecor)
}

object KotlinFieldNameDecor : PurposeDecor {
    override val purposeDecorName: PurposeDecorName = PurposeDecorName("fieldName")
    override val purposeDecorType: DecorType = TextDecorType
}

object KotlinFieldTypeDecor : PurposeDecor {
    override val purposeDecorName: PurposeDecorName = PurposeDecorName("fieldType")
    override val purposeDecorType: DecorType = EnumerationDecorType(
        listOf(
            DecorTypeEnumerationValue("kotlin.String"),
            DecorTypeEnumerationValue("kotlin.Int"),
            DecorTypeEnumerationValue("kotlin.Boolean"),
        )
    )
}
