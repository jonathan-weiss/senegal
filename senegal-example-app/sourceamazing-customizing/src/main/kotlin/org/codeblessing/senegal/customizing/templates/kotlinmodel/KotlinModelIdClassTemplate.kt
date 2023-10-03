package org.codeblessing.senegal.customizing.templates.kotlinmodel

import org.codeblessing.sourceamazing.tools.StringIdentHelper.identForMarker

object KotlinModelIdClassTemplate {

    fun fillTemplate(kotlinModelClass: org.codeblessing.senegal.customizing.templates.kotlinmodel.KotlinModelClass): String {
        return """
        package ${kotlinModelClass.kotlinPackage}

        import org.codeblessing.senegal.shareddomain.Id
        import org.codeblessing.senegal.shareddomain.UUIDIdFactory
        import java.util.UUID

        data class ${kotlinModelClass.idFieldType}(override val value: UUID) : Id<UUID> {
            companion object : UUIDIdFactory<${kotlinModelClass.idFieldType}>(${kotlinModelClass.idFieldType}::class)
        }
        """.identForMarker()
    }
}
