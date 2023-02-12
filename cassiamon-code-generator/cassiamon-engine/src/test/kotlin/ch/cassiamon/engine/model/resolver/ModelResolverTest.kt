package ch.cassiamon.engine.model.resolver

import ch.cassiamon.engine.TestFixtures
import ch.cassiamon.engine.model.ModelInputData
import ch.cassiamon.engine.schema.Schema
import ch.cassiamon.engine.schema.WiredConceptSchema
import ch.cassiamon.engine.schema.WiredFacetSchema
import ch.cassiamon.pluginapi.*
import ch.cassiamon.pluginapi.factory.ConceptSchemaFactory
import ch.cassiamon.pluginapi.factory.FacetSchemaFactory
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ModelResolverTest {

    @Test
    fun expandToModelGraphAndValidate() {
        // arrange
        val facetFunctions: Set<FacetFunction> = emptySet()
        val schema = TestFixtures.wiredConceptSchemas
        // val modelInputData = createModelInputData()

        // act
        // val modelGraph = ModelResolver.expandToModelGraphAndValidate(schema, modelInputData, facetFunctions)

        // assert
        // Assertions.assertNotNull(modelGraph)


    }

    private fun createModelInputData(schema: Schema): ModelInputData {
        return ModelInputData(emptySet())
    }

    private fun createSchemaForSimpleTestDatabaseTable(): Schema {
        val databaseTableConceptName = ConceptName.of("DatabaseTable")
        val databaseTableFieldConceptName = ConceptName.of("DatabaseField")
        val tableNameFacetName = FacetName.of("TableName")
        val tableFieldNameFacetName = FacetName.of("FieldName")
        val tableFieldTypeFacetName = FacetName.of("FieldType")
        val tableFieldLengthFacetName = FacetName.of("FieldLength")

        val databaseTableConcept = ConceptSchemaFactory.createConceptSchema(databaseTableConceptName)
        val tableNameFacet = FacetSchemaFactory.StringFacetSchemaFactory.createFacet(tableNameFacetName, databaseTableConceptName)

        val databaseTableFieldConcept = ConceptSchemaFactory.createConceptSchema(databaseTableFieldConceptName)
        val tableFieldNameFacet = FacetSchemaFactory.StringFacetSchemaFactory.createFacet(tableFieldNameFacetName, databaseTableFieldConceptName)
        val tableFieldTypeFacet = FacetSchemaFactory.StringFacetSchemaFactory.createFacet(tableFieldTypeFacetName, databaseTableFieldConceptName)
        val tableFieldLengthFacet = FacetSchemaFactory.IntegerFacetSchemaFactory.createFacet(tableFieldLengthFacetName, databaseTableFieldConceptName)

        val wiredConceptSchemas = setOf(
            WiredConceptSchema(databaseTableConcept, setOf(WiredFacetSchema(tableNameFacet))),
            WiredConceptSchema(databaseTableFieldConcept,
                setOf(
                    WiredFacetSchema(tableFieldNameFacet),
                    WiredFacetSchema(tableFieldTypeFacet),
                    WiredFacetSchema(tableFieldLengthFacet)
                )
            )
        )
        return Schema(wiredConceptSchemas)
    }
}
