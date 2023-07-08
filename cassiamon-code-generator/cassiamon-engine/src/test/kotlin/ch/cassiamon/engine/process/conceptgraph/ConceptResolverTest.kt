package ch.cassiamon.engine.process.conceptgraph

import ch.cassiamon.api.process.schema.ConceptIdentifier
import ch.cassiamon.api.process.schema.ConceptName
import ch.cassiamon.api.process.schema.FacetName
import ch.cassiamon.api.process.schema.SchemaAccess
import ch.cassiamon.api.process.schema.annotations.ChildConcepts
import ch.cassiamon.api.process.schema.annotations.Concept
import ch.cassiamon.api.process.schema.annotations.Facet
import ch.cassiamon.api.process.schema.annotations.Schema
import ch.cassiamon.engine.process.datacollection.ConceptDataCollector
import ch.cassiamon.engine.process.schema.SchemaCreator
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

private const val databaseTableConceptConst = "DatabaseTable"
private const val databaseFieldConceptConst = "DatabaseField"

private const val tableNameFacetConst = "TableName"
private const val fieldNameFacetConst = "FieldName"
private const val fieldTypeFacetConst = "FieldType"
private const val fieldLengthFacetConst = "FieldLength"

class ConceptResolverTest {

    private val databaseTableConceptName = ConceptName.of(databaseTableConceptConst)
    private val databaseTableFieldConceptName = ConceptName.of(databaseFieldConceptConst)

    private val tableNameFacetName = FacetName.of(tableNameFacetConst)
    private val tableFieldNameFacetName = FacetName.of(fieldNameFacetConst)
    private val tableFieldTypeFacetName = FacetName.of(fieldTypeFacetConst)
    private val tableFieldLengthFacetName = FacetName.of(fieldLengthFacetConst)

    @Schema
    interface DatabaseSchema {

        @ChildConcepts(DatabaseTableConcept::class)
        fun getTables(): List<DatabaseTableConcept>
    }

    @Concept(databaseTableConceptConst)
    interface DatabaseTableConcept {

        @Facet(tableNameFacetConst)
        fun getDatabaseName(): String

        @ChildConcepts(DatabaseFieldConcept::class)
        fun getFields(): List<DatabaseFieldConcept>

    }

    @Concept(databaseFieldConceptConst)
    interface DatabaseFieldConcept {
        @Facet(fieldNameFacetConst)
        fun getFieldName(): String
        @Facet(fieldTypeFacetConst)
        fun getFieldType(): String
        @Facet(fieldLengthFacetConst)
        fun getFieldLength(): Int
    }

    private val schema = SchemaCreator.createSchemaFromSchemaDefinitionClass(DatabaseSchema::class.java)

    private fun createCollector(schema: SchemaAccess): ConceptDataCollector {
        return ConceptDataCollector(schema)
    }


    @Test
    fun `validate empty concept`() {
        // arrange
        val conceptDataCollector = createCollector(schema)

        // act
        val conceptGraph = ConceptResolver.validateAndResolveConcepts(schema, conceptDataCollector.provideConceptData())

        // assert
        assertNotNull(conceptGraph)
    }

    @Test
    fun `validate simple concept data`() {
        // arrange
        val conceptDataCollector = createCollector(schema)
        val personTableId = ConceptIdentifier.of("Person")

        conceptDataCollector.existingOrNewConceptData(
            conceptName = databaseTableConceptName,
            conceptIdentifier = personTableId,
            parentConceptIdentifier = null,
        ).addOrReplaceFacetValue(tableNameFacetName, "Person")


        // act
        val conceptGraph = ConceptResolver.validateAndResolveConcepts(schema, conceptDataCollector.provideConceptData())

        // assert
        assertNotNull(conceptGraph)
        assertEquals(personTableId, conceptGraph.conceptsByConceptName(databaseTableConceptName).single().conceptIdentifier)
    }

}
