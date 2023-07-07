package ch.cassiamon.engine.domain.schema

import ch.cassiamon.api.schema.DomainUnitSchemaHelper
import ch.cassiamon.api.schema.SchemaAccess

class DomainUnitSchemaHelperImpl(): DomainUnitSchemaHelper {

    override fun <S : Any> createDomainUnitSchema(schemaDefinitionClass: Class<S>): SchemaAccess {
        return SchemaCreator.createSchemaFromSchemaDefinitionClass(schemaDefinitionClass)
    }

}
