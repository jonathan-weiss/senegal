package ch.cassiamon.engine.model.graph

import ch.cassiamon.engine.TestFixtures
import ch.cassiamon.engine.model.inputsource.ModelInputDataCollector
import ch.cassiamon.engine.model.types.ConceptReferenceFacetValue
import ch.cassiamon.engine.model.types.IntegerNumberFacetValue
import ch.cassiamon.engine.model.types.TextFacetValue
import ch.cassiamon.pluginapi.model.ConceptIdentifier
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class CalculatedModelCreatorTest {

    private val databaseTableConceptName = TestFixtures.databaseTableConceptName
    private val databaseTableFieldConceptName = TestFixtures.databaseTableFieldConceptName
    private val databaseTableFieldForeignKeyConceptName = TestFixtures.databaseTableFieldForeignKeyConceptName
    private val tableNameFacetName = TestFixtures.tableNameFacetName
    private val tableFieldNameFacetName = TestFixtures.tableFieldNameFacetName
    private val tableFieldTypeFacetName = TestFixtures.tableFieldTypeFacetName
    private val tableFieldLengthFacetName = TestFixtures.tableFieldLengthFacetName
    private val tableFieldForeignKeyConceptIdFacetName = TestFixtures.tableFieldForeignKeyConceptIdFacetName
    private val tableNameAndFieldNameFacetName = TestFixtures.tableNameAndFieldNameFacetName

    @Test
    fun `calculate a simple graph`() {
        // arrange
        val schema = TestFixtures.createTestFixtureSchema()
        val modelInputDataCollector = ModelInputDataCollector()

        val personTableId = ConceptIdentifier.of("Person")
        modelInputDataCollector.attachConceptData(
            conceptName = databaseTableConceptName,
            conceptIdentifier = personTableId,
            parentConceptIdentifier = null,
            facetValues = arrayOf(
                Pair(tableNameFacetName, TextFacetValue("Person"))
            )
        )


        // act
        val modelGraph = ModelCalculator.calculateModel(schema, modelInputDataCollector.provideModelInputData())

        // assert
        assertNotNull(modelGraph)
    }

    @Test
    fun `calculate a graph with valid hierarchical and reference dependencies`() {
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
                Pair(tableFieldLengthFacetName, IntegerNumberFacetValue(255)),
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
                Pair(tableFieldLengthFacetName, IntegerNumberFacetValue(128)),
            )
        )

        val addressPersonForeignKeyPointerFieldId = ConceptIdentifier.of("Address_fkPerson_pointer")
        modelInputDataCollector.attachConceptData(
            conceptName = databaseTableFieldForeignKeyConceptName,
            conceptIdentifier = addressPersonForeignKeyPointerFieldId,
            parentConceptIdentifier = addressPersonForeignKeyFieldId,
            facetValues = arrayOf(
                Pair(tableFieldForeignKeyConceptIdFacetName, ConceptReferenceFacetValue(personTableId)),
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
                Pair(tableFieldLengthFacetName, IntegerNumberFacetValue(255)),
            )
        )

        // act
        val modelGraph = ModelCalculator.calculateModel(schema, modelInputDataCollector.provideModelInputData())

        // assert
        assertNotNull(modelGraph)
    }


    @Test
    fun `calculate a graph with invalid cyclic hierarchical dependency`() {
        // arrange
        val schema = TestFixtures.createTestFixtureSchema()
        val modelInputDataCollector = ModelInputDataCollector()

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
                Pair(tableFieldLengthFacetName, IntegerNumberFacetValue(255)),
            )
        )


        val addressToAddressForeignKeyFieldId = ConceptIdentifier.of("Address_fk")
        modelInputDataCollector.attachConceptData(
            conceptName = databaseTableFieldConceptName,
            conceptIdentifier = addressToAddressForeignKeyFieldId,
            parentConceptIdentifier = addressTableId,
            facetValues = arrayOf(
                Pair(tableFieldNameFacetName, TextFacetValue("fkAddressId")),
                Pair(tableFieldTypeFacetName, TextFacetValue("UUID")),
                Pair(tableFieldLengthFacetName, IntegerNumberFacetValue(128)),
            )
        )

        modelInputDataCollector.attachConceptData(
            conceptName = databaseTableFieldForeignKeyConceptName,
            conceptIdentifier = ConceptIdentifier.of("Address_to_Address_fk"),
            parentConceptIdentifier = addressToAddressForeignKeyFieldId,
            facetValues = arrayOf(
                Pair(tableFieldForeignKeyConceptIdFacetName, ConceptReferenceFacetValue(addressTableId)),
            )
        )

        // act
        val modelGraph = ModelCalculator.calculateModel(schema, modelInputDataCollector.provideModelInputData())

        // assert
        assertNotNull(modelGraph)
    }

}
