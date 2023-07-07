package ch.cassiamon.api.schema

interface DomainUnitSchemaHelper {
    fun <S: Any> createDomainUnitSchema(schemaDefinitionClass: Class<S>): SchemaAccess
}
