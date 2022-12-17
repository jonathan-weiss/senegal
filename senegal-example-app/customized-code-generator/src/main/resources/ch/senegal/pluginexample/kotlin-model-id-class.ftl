package ${templateModel.KotlinModelPackage}

import ch.senegal.example.shareddomain.Id
import ch.senegal.example.shareddomain.UUIDIdFactory
import java.util.UUID

data class ${templateModel.KotlinModelIdFieldType}(override val value: UUID) : Id<UUID> {
    companion object : UUIDIdFactory<${templateModel.KotlinModelIdFieldType}>(${templateModel.KotlinModelIdFieldType}::class)
}
