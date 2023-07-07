package ch.cassiamon.engine.process.schema

import ch.cassiamon.api.process.schema.DomainUnitSchemaHelper
import ch.cassiamon.api.process.schema.SchemaAccess

class DomainUnitSchemaHelperImpl(): DomainUnitSchemaHelper {

    override fun <S : Any> createDomainUnitSchema(schemaDefinitionClass: Class<S>): SchemaAccess {
        return SchemaCreator.createSchemaFromSchemaDefinitionClass(schemaDefinitionClass)
    }

}
