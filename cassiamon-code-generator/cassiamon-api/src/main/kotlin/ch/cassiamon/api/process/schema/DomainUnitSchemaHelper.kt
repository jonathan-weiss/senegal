package ch.cassiamon.api.process.schema

import kotlin.jvm.Throws

interface DomainUnitSchemaHelper {
    @Throws(MalformedSchemaException::class)
    fun <S: Any> createDomainUnitSchema(schemaDefinitionClass: Class<S>): SchemaAccess
}
