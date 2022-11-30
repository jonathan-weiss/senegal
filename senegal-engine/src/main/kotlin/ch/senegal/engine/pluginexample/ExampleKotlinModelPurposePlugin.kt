package ch.senegal.engine.pluginexample

import ch.senegal.engine.plugin.*


object KotlinModelPurposePlugin: Purpose {
    override val purposeName: PurposeName = PurposeName("kotlinModel")
    override val enclosingConceptName = EntityConceptPlugin.conceptName
    override val purposeDecors: Set<PurposeDecor> = setOf(KotlinModelClassnameDecor, KotlinModelPackageDecor)
}

object KotlinModelClassnameDecor: PurposeDecor {
    override val purposeDecorName: PurposeDecorName = PurposeDecorName("className")
    override val purposeDecorType: DecorType = TextDecorType
}

object KotlinModelPackageDecor: PurposeDecor {
    override val purposeDecorName: PurposeDecorName = PurposeDecorName("package")
    override val purposeDecorType: DecorType = TextDecorType
}
