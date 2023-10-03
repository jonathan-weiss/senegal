package ch.cassiamon.exampleapp.customizing.templates.kotlinmodel

import org.codeblessing.sourceamazing.tools.StringIdentHelper.identForMarker

object KotlinModelIdClassTemplate {

    fun fillTemplate(kotlinModelClass: KotlinModelClass): String {
        return """
        package ${kotlinModelClass.kotlinPackage}

        import ch.senegal.example.shareddomain.Id
        import ch.senegal.example.shareddomain.UUIDIdFactory
        import java.util.UUID

        data class ${kotlinModelClass.idFieldType}(override val value: UUID) : Id<UUID> {
            companion object : UUIDIdFactory<${kotlinModelClass.idFieldType}>(${kotlinModelClass.idFieldType}::class)
        }
        """.identForMarker()
    }
}
