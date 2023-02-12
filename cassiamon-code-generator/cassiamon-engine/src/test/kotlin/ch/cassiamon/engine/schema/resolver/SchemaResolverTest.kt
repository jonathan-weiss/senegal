package ch.cassiamon.engine.schema.resolver

import ch.cassiamon.pluginapi.ConceptSchema
import ch.cassiamon.pluginapi.FacetSchema
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SchemaResolverTest {

    @Test
    fun testSchemaResolver() {
        // arrange
        val conceptSchemas: Set<ConceptSchema> = emptySet()
        val facetSchemas: Set<FacetSchema> = emptySet()

        // act
        val resolvedSchema = SchemaResolver.resolveSchema(conceptSchemas, facetSchemas)

        // assert
        Assertions.assertNotNull(resolvedSchema)

    }

}

