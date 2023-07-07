package ch.cassiamon.api.process.schema

interface DomainUnitSchemaHelper {
    fun <S: Any> createDomainUnitSchema(schemaDefinitionClass: Class<S>): SchemaAccess
}
