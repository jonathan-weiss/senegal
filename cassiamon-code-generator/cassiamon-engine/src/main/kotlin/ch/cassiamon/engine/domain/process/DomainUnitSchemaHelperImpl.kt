package ch.cassiamon.engine.domain.process

import ch.cassiamon.api.registration.DomainUnitSchemaHelper
import ch.cassiamon.api.schema.SchemaAccess
import ch.cassiamon.engine.ProcessSession
import ch.cassiamon.engine.domain.SchemaCreator

class DomainUnitSchemaHelperImpl(): DomainUnitSchemaHelper {

    override fun <S : Any> createDomainUnitSchema(schemaDefinitionClass: Class<S>): SchemaAccess {
        return SchemaCreator.createSchemaFromSchemaDefinitionClass(schemaDefinitionClass)
    }

}
