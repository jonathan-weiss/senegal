package ch.cassiamon.engine.model.validator

import ch.cassiamon.engine.TestFixtures
import ch.cassiamon.engine.inputsource.ModelConceptInputDataEntry
import ch.cassiamon.engine.inputsource.ModelInputDataCollector
import ch.cassiamon.engine.schema.Schema
import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.model.ConceptIdentifier
import ch.cassiamon.pluginapi.model.exceptions.ConceptNotKnownModelException
import ch.cassiamon.pluginapi.model.exceptions.ConceptParentInvalidModelException
import ch.cassiamon.pluginapi.model.exceptions.InvalidFacetConfigurationModelException
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import kotlin.reflect.KClass

class ConceptModelNodeValidatorTest {

    private val databaseTableConceptName = TestFixtures.databaseTableConceptName
    private val databaseTableFieldConceptName = TestFixtures.databaseTableFieldConceptName
    private val tableNameFacetName = TestFixtures.tableNameFacet
    private val tableFieldNameFacetName = TestFixtures.tableFieldNameFacet
    private val tableFieldTypeFacetName = TestFixtures.tableFieldTypeFacet
    private val tableFieldLengthFacetName = TestFixtures.tableFieldLengthFacet
    private val tableNameAndFieldNameFacetName = TestFixtures.tableNameAndFieldNameFacet

    @Test
    fun `validate a valid singe root concept entry`() {
        // arrange
        val schema = TestFixtures.createTestFixtureSchema()
        val modelInputDataCollector = ModelInputDataCollector()
        val personTableId = ConceptIdentifier.of("Person")

        modelInputDataCollector.newConceptData(
            conceptName = databaseTableConceptName,
            conceptIdentifier = personTableId,
            parentConceptIdentifier = null,
        ).addFacetValue(tableNameFacetName, "Person").attach()

        // act + assert
        testModelNodeValidator(personTableId, modelInputDataCollector, schema)
    }

    @Test
    fun `validate a invalid concept entry with unknown concept name`() {
        // arrange
        val schema = TestFixtures.createTestFixtureSchema()
        val modelInputDataCollector = ModelInputDataCollector()
        val personTableId = ConceptIdentifier.of("Person")

        modelInputDataCollector.newConceptData(
            conceptName = ConceptName.of("UnknownConcept"), // unknown concept
            conceptIdentifier = personTableId,
            parentConceptIdentifier = null,
        ).addFacetValue(tableNameFacetName, "Person").attach()

        // act + assert
        testModelNodeValidator(personTableId, modelInputDataCollector, schema, ConceptNotKnownModelException::class)
    }


    @Test
    fun `validate a valid child concept entry`() {
        // arrange
        val schema = TestFixtures.createTestFixtureSchema()
        val modelInputDataCollector = ModelInputDataCollector()
        val personTableId = ConceptIdentifier.of("Person")

        modelInputDataCollector.newConceptData(
            conceptName = databaseTableConceptName,
            conceptIdentifier = personTableId,
            parentConceptIdentifier = null,
        ).addFacetValue(tableNameFacetName, "Person").attach()

        val personFirstnameFieldId = ConceptIdentifier.of("Person_firstname")
        modelInputDataCollector.newConceptData(
            conceptName = databaseTableFieldConceptName,
            conceptIdentifier = personFirstnameFieldId,
            parentConceptIdentifier = personTableId,
        )
            .addFacetValue(tableFieldNameFacetName, "firstname")
            .addFacetValue(tableFieldTypeFacetName, "VARCHAR")
            .addFacetValue(tableFieldLengthFacetName, 255)
            .attach()


        // act + assert
        testModelNodeValidator(personFirstnameFieldId, modelInputDataCollector, schema)
    }


    @Test
    fun `validate a invalid root concept entry with unexpected parent concept`() {
        // arrange
        val schema = TestFixtures.createTestFixtureSchema()
        val modelInputDataCollector = ModelInputDataCollector()
        val personTableId = ConceptIdentifier.of("Person")

        modelInputDataCollector.newConceptData(
            conceptName = databaseTableConceptName,
            conceptIdentifier = personTableId,
            parentConceptIdentifier = ConceptIdentifier.of("InvalidParentIdentifier"), // wrong concept
        ).addFacetValue(tableNameFacetName, "Person").attach()

        // act + assert
        testModelNodeValidator(personTableId, modelInputDataCollector, schema, ConceptParentInvalidModelException::class)
    }


    @Test
    fun `validate a invalid child concept entry with expected but missing parent concept`() {
        // arrange
        val schema = TestFixtures.createTestFixtureSchema()
        val modelInputDataCollector = ModelInputDataCollector()

        val personFirstnameFieldId = ConceptIdentifier.of("Person_firstname")
        modelInputDataCollector.newConceptData(
            conceptName = databaseTableFieldConceptName,
            conceptIdentifier = personFirstnameFieldId,
            parentConceptIdentifier = null, // parent concept missing
        )
            .addFacetValue(tableFieldNameFacetName, "firstname")
            .addFacetValue(tableFieldTypeFacetName, "VARCHAR")
            .addFacetValue(tableFieldLengthFacetName, 255)
            .attach()



        // act + assert
        testModelNodeValidator(personFirstnameFieldId, modelInputDataCollector, schema, ConceptParentInvalidModelException::class)
    }

    @Test
    fun `validate a concept with missing facet`() {
        // arrange
        val schema = TestFixtures.createTestFixtureSchema()
        val modelInputDataCollector = ModelInputDataCollector()
        val personTableId = ConceptIdentifier.of("Person")

        modelInputDataCollector.newConceptData(
            conceptName = databaseTableConceptName,
            conceptIdentifier = personTableId,
            parentConceptIdentifier = null,
        ).attach()// facet tableNameFacetName missing

        // act + assert
        testModelNodeValidator(personTableId, modelInputDataCollector, schema, InvalidFacetConfigurationModelException::class)
    }

    @Test
    fun `validate a concept with wrong additional facet`() {
        // arrange
        val schema = TestFixtures.createTestFixtureSchema()
        val modelInputDataCollector = ModelInputDataCollector()

        val personTableId = ConceptIdentifier.of("Person")
        val personFirstnameFieldId = ConceptIdentifier.of("Person_firstname")
        modelInputDataCollector.newConceptData(
            conceptName = databaseTableFieldConceptName,
            conceptIdentifier = personFirstnameFieldId,
            parentConceptIdentifier = personTableId,
        )
            .addFacetValue(tableFieldNameFacetName, "firstname")
            .addFacetValue(tableFieldNameFacetName, "foobar") // this facet is not allowed in this concept
            .addFacetValue(tableFieldTypeFacetName, "VARCHAR")
            .addFacetValue(tableFieldLengthFacetName, 255)
            .attach()

        // act + assert
        testModelNodeValidator(personFirstnameFieldId, modelInputDataCollector, schema, InvalidFacetConfigurationModelException::class)
    }

//    @Test
//    fun `validate a concept with a value for calculated facet`() {
//        // arrange
//        val schema = TestFixtures.createTestFixtureSchema()
//        val modelInputDataCollector = ModelInputDataCollector()
//
//        val personTableId = ConceptIdentifier.of("Person")
//        val personFirstnameFieldId = ConceptIdentifier.of("Person_firstname")
//        modelInputDataCollector.newConceptData(
//            conceptName = databaseTableFieldConceptName,
//            conceptIdentifier = personFirstnameFieldId,
//            parentConceptIdentifier = personTableId,
//        )
//            .addTextFacetValue(tableFieldNameFacetName, "firstname")
//            .addTextFacetValue(tableFieldTypeFacetName, "VARCHAR")
//            .addIntegerNumberFacetValue(tableFieldLengthFacetName, 255)
//            .addTextFacetValue(tableNameAndFieldNameFacetName, "manual-data") //wrong as a calculated facet
//            .attach()
//
//        // act + assert
//        testModelNodeValidator(personFirstnameFieldId, modelInputDataCollector, schema, InvalidFacetConfigurationModelException::class)
//    }

    private fun testModelNodeValidator(entryId: ConceptIdentifier, collector: ModelInputDataCollector, schema: Schema, expectedExceptionType: KClass<out Throwable>? = null) {
        val entryToTest = entryByConceptIdentifier(entryId, collector)
        if(expectedExceptionType == null) {
            ConceptModelNodeValidator.validateSingleEntry(
                schema = schema,
                entry = entryToTest
            )
        } else {
            assertThrows(expectedExceptionType.java) {
                ConceptModelNodeValidator.validateSingleEntry(
                    schema = schema,
                    entry = entryToTest
                )
            }
        }
    }

    private fun entryByConceptIdentifier(id: ConceptIdentifier, collector: ModelInputDataCollector): ModelConceptInputDataEntry {
        return collector.provideModelInputData().entries.firstOrNull { it.conceptIdentifier == id } ?: fail("Test concept $id not found.")
    }

}
