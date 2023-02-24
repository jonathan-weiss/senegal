package ch.cassiamon.engine.model.validator

import ch.cassiamon.engine.TestFixtures
import ch.cassiamon.engine.inputsource.ModelConceptInputDataEntry
import ch.cassiamon.engine.inputsource.ModelInputDataCollector
import ch.cassiamon.engine.model.types.IntegerNumberFacetValue
import ch.cassiamon.engine.model.types.TextFacetValue
import ch.cassiamon.engine.schema.types.Schema
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
    private val tableNameFacetName = TestFixtures.tableNameFacetName
    private val tableFieldNameFacetName = TestFixtures.tableFieldNameFacetName
    private val tableFieldTypeFacetName = TestFixtures.tableFieldTypeFacetName
    private val tableFieldLengthFacetName = TestFixtures.tableFieldLengthFacetName
    private val tableNameAndFieldNameFacetName = TestFixtures.tableNameAndFieldNameFacetName

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
        ).withFacetValue(tableNameFacetName, TextFacetValue("Person")).attach()

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
        ).withFacetValue(tableNameFacetName, TextFacetValue("Person")).attach()

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
        ).withFacetValue(tableNameFacetName, TextFacetValue("Person")).attach()

        val personFirstnameFieldId = ConceptIdentifier.of("Person_firstname")
        modelInputDataCollector.newConceptData(
            conceptName = databaseTableFieldConceptName,
            conceptIdentifier = personFirstnameFieldId,
            parentConceptIdentifier = personTableId,
        ).withFacetValues(
            Pair(tableFieldNameFacetName, TextFacetValue("firstname")),
            Pair(tableFieldTypeFacetName, TextFacetValue("VARCHAR")),
            Pair(tableFieldLengthFacetName, IntegerNumberFacetValue(255)),
        ).attach()


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
        ).withFacetValue(tableNameFacetName, TextFacetValue("Person")).attach()

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
        ).withFacetValues(
            Pair(tableFieldNameFacetName, TextFacetValue("firstname")),
            Pair(tableFieldTypeFacetName, TextFacetValue("VARCHAR")),
            Pair(tableFieldLengthFacetName, TextFacetValue("255")),
            ).attach()


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
        ).withFacetValues(
            Pair(tableFieldNameFacetName, TextFacetValue("firstname")),
            Pair(tableNameFacetName, TextFacetValue("foobar")), // this facet is not allowed in this concept
            Pair(tableFieldTypeFacetName, TextFacetValue("VARCHAR")),
            Pair(tableFieldLengthFacetName, IntegerNumberFacetValue(255)),
            ).attach()

        // act + assert
        testModelNodeValidator(personFirstnameFieldId, modelInputDataCollector, schema, InvalidFacetConfigurationModelException::class)
    }

    @Test
    fun `validate a concept with a value for calculated facet`() {
        // arrange
        val schema = TestFixtures.createTestFixtureSchema()
        val modelInputDataCollector = ModelInputDataCollector()

        val personTableId = ConceptIdentifier.of("Person")
        val personFirstnameFieldId = ConceptIdentifier.of("Person_firstname")
        modelInputDataCollector.newConceptData(
            conceptName = databaseTableFieldConceptName,
            conceptIdentifier = personFirstnameFieldId,
            parentConceptIdentifier = personTableId,
        ).withFacetValues(
            Pair(tableFieldNameFacetName, TextFacetValue("firstname")),
            Pair(tableFieldTypeFacetName, TextFacetValue("VARCHAR")),
            Pair(tableFieldLengthFacetName, IntegerNumberFacetValue(255)),
            Pair(tableNameAndFieldNameFacetName, TextFacetValue("manual-data")), //wrong as a calculated facet
        ).attach()

        // act + assert
        testModelNodeValidator(personFirstnameFieldId, modelInputDataCollector, schema, InvalidFacetConfigurationModelException::class)
    }

    @Test
    fun `validate a concept with wrong type facet`() {
        // arrange
        val schema = TestFixtures.createTestFixtureSchema()
        val modelInputDataCollector = ModelInputDataCollector()

        val personTableId = ConceptIdentifier.of("Person")
        val personFirstnameFieldId = ConceptIdentifier.of("Person_firstname")
        modelInputDataCollector.newConceptData(
            conceptName = databaseTableFieldConceptName,
            conceptIdentifier = personFirstnameFieldId,
            parentConceptIdentifier = personTableId,
        ).withFacetValues(
            Pair(tableFieldNameFacetName, TextFacetValue("firstname")),
            Pair(tableFieldTypeFacetName, TextFacetValue("VARCHAR")),
            Pair(tableFieldLengthFacetName, TextFacetValue("255")), // wrong type
        ).attach()

        // act + assert
        testModelNodeValidator(personFirstnameFieldId, modelInputDataCollector, schema, InvalidFacetConfigurationModelException::class)
    }

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
