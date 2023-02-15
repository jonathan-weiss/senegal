package ch.cassiamon.engine.model.graph

import ch.cassiamon.engine.TestFixtures
import ch.cassiamon.engine.model.inputsource.ModelConceptInputDataEntry
import ch.cassiamon.engine.model.inputsource.ModelInputDataCollector
import ch.cassiamon.engine.model.types.ConceptReferenceFacetValue
import ch.cassiamon.engine.model.types.TextFacetValue
import ch.cassiamon.pluginapi.model.ConceptIdentifier
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class ModelGraphCreatorTest {

    private val databaseTableConceptName = TestFixtures.databaseTableConceptName
    private val databaseTableFieldConceptName = TestFixtures.databaseTableFieldConceptName
    private val tableNameFacetName = TestFixtures.tableNameFacetName
    private val tableFieldNameFacetName = TestFixtures.tableFieldNameFacetName
    private val tableFieldTypeFacetName = TestFixtures.tableFieldTypeFacetName
    private val tableFieldLengthFacetName = TestFixtures.tableFieldLengthFacetName
    private val tableFieldForeignKeyFacetName = TestFixtures.tableFieldForeignKeyFacetName
    private val tableNameAndFieldNameFacetName = TestFixtures.tableNameAndFieldNameFacetName

    @Test
    fun `calculate a simple graph`() {
        // arrange
        val schema = TestFixtures.createTestFixtureSchema()
        val modelInputData = TestFixtures.createModelInputData()

        // act
        val modelGraph = ModelGraphCreator.calculateGraph(schema, modelInputData)

        // assert
        assertNotNull(modelGraph)
    }

    @Test
    fun `calculate a graph with hierarchical and reference dependencies`() {
        // arrange
        val schema = TestFixtures.createTestFixtureSchema()

        val modelInputDataCollector = ModelInputDataCollector()

        val personTableId = ConceptIdentifier.of("Person")
        val addressTableId = ConceptIdentifier.of("Address")


        // ADDRESS table
        modelInputDataCollector.attachConceptData(
            conceptName = databaseTableConceptName,
            conceptIdentifier = addressTableId,
            parentConceptIdentifier = null,
            facetValues = arrayOf(
                Pair(tableNameFacetName, TextFacetValue("Address")),
            )
        )

        // ADDRESS fields
        val addressStreetFieldId = ConceptIdentifier.of("Address_street")
        modelInputDataCollector.attachConceptData(
            conceptName = databaseTableFieldConceptName,
            conceptIdentifier = addressStreetFieldId,
            parentConceptIdentifier = addressTableId,
            facetValues = arrayOf(
                Pair(tableFieldNameFacetName, TextFacetValue("street")),
                Pair(tableFieldTypeFacetName, TextFacetValue("VARCHAR")),
                Pair(tableFieldLengthFacetName, TextFacetValue("255")),
            )
        )


        val addressPersonForeignKeyFieldId = ConceptIdentifier.of("Address_fkPerson")
        modelInputDataCollector.attachConceptData(
            conceptName = databaseTableFieldConceptName,
            conceptIdentifier = addressPersonForeignKeyFieldId,
            parentConceptIdentifier = addressTableId,
            facetValues = arrayOf(
                Pair(tableFieldNameFacetName, TextFacetValue("fkPersonId")),
                Pair(tableFieldTypeFacetName, TextFacetValue("UUID")),
                Pair(tableFieldLengthFacetName, TextFacetValue("128")),
                Pair(tableFieldForeignKeyFacetName, ConceptReferenceFacetValue(personTableId)),
            )
        )

        // PERSON table
        modelInputDataCollector.attachConceptData(
            conceptName = databaseTableConceptName,
            conceptIdentifier = personTableId,
            parentConceptIdentifier = null,
            facetValues = arrayOf(
                Pair(tableNameFacetName, TextFacetValue("Person"))
            )
        )

        // PERSON fields
        val personFirstnameFieldId = ConceptIdentifier.of("Person_firstname")
        modelInputDataCollector.attachConceptData(
            conceptName = databaseTableFieldConceptName,
            conceptIdentifier = personFirstnameFieldId,
            parentConceptIdentifier = personTableId,
            facetValues = arrayOf(
                Pair(tableFieldNameFacetName, TextFacetValue("firstname")),
                Pair(tableFieldTypeFacetName, TextFacetValue("VARCHAR")),
                Pair(tableFieldLengthFacetName, TextFacetValue("255")),
            )
        )

        // act
        val modelGraph = ModelGraphCreator.calculateGraph(schema, modelInputDataCollector.provideModelInputData())

        // assert
        assertNotNull(modelGraph)
    }


}
