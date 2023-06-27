package ch.cassiamon.engine.domain

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SchemaCreatorTest {

    interface EmptySchemaDefinitionClass

    @Test
    fun createSchemaFromEmptyClass() {
        val schema = SchemaCreator.createSchemaFromSchemaDefinitionClass(EmptySchemaDefinitionClass::class.java)
        Assertions.assertEquals(0, schema.numberOfConcepts())
    }
}
