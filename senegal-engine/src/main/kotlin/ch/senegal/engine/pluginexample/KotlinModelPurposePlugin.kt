package ch.senegal.engine.pluginexample

import ch.senegal.engine.plugin.*


class KotlinModelPurposePlugin: Purpose {

    companion object {
        val kotlinModelPurposeName: PurposeName = PurposeName("kotlinModelClass")
    }

    override val purposeName: PurposeName = kotlinModelPurposeName

    override val enclosingConceptName = EntityConceptPlugin.entityConceptName

    override val purposeDecors: Set<PurposeDecor>
        get() = setOf(KotlinModelClassnameDecor(), KotlinModelPackageDecor())
}

class KotlinModelClassnameDecor: PurposeDecor {

    companion object {
        val classNameDecorName: PurposeDecorName = PurposeDecorName("className")
    }


    override val purposeDecorName: PurposeDecorName
        get() = classNameDecorName
}

class KotlinModelPackageDecor: PurposeDecor {

    companion object {
        val packageDecorName: PurposeDecorName = PurposeDecorName("package")
    }


    override val purposeDecorName: PurposeDecorName
        get() = packageDecorName
}
