package ch.cassiamon.api.registration

import ch.cassiamon.api.schema.SchemaAccess

interface DomainUnitSchemaHelper {
    fun <S: Any> createDomainUnitSchema(schemaDefinitionClass: Class<S>): SchemaAccess
}
