package org.codeblessing.senegal.codegen.templates.kotlinmodel

object KotlinModelIdClassTemplate {

    fun fillTemplate(kotlinModelClass: KotlinModelClass): String {
        return """
        package ${kotlinModelClass.kotlinPackage}

        import org.codeblessing.senegal.shareddomain.Id
        import org.codeblessing.senegal.shareddomain.UUIDIdFactory
        import java.util.UUID

        data class ${kotlinModelClass.idFieldType}(override val value: UUID) : Id<UUID> {
            companion object : UUIDIdFactory<${kotlinModelClass.idFieldType}>(${kotlinModelClass.idFieldType}::class)
        }
        """
    }
}
