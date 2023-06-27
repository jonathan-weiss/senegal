package ch.cassiamon.engine.domain

object SchemaCreator {

    fun createSchemaFromSchemaDefinitionClass(schemaDefinitionClass: Class<*>): Schema {
        return Schema(emptyMap())
    }
}
