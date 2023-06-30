package ch.cassiamon.engine.domain.datacollection

import ch.cassiamon.api.ConceptName
import ch.cassiamon.api.FacetName
import ch.cassiamon.api.annotations.ChildConcepts
import ch.cassiamon.api.annotations.Concept
import ch.cassiamon.api.annotations.InputFacet
import ch.cassiamon.api.annotations.Schema
import ch.cassiamon.api.model.ConceptIdentifier
import ch.cassiamon.api.model.exceptions.ConceptNotKnownModelException
import ch.cassiamon.api.model.exceptions.ConceptParentInvalidModelException
import ch.cassiamon.api.model.exceptions.InvalidFacetConfigurationModelException
import ch.cassiamon.api.schema.SchemaAccess
import ch.cassiamon.engine.domain.SchemaCreator
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.reflect.KClass

const val databaseTableConceptConst = "DatabaseTable"
const val databaseFieldConceptConst = "DatabaseField"

const val tableNameFacetConst = "TableName"
const val fieldNameFacetConst = "FieldName"
const val fieldTypeFacetConst = "FieldType"
const val fieldLengthFacetConst = "FieldLength"
class ConceptDataValidatorTest {

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
        
        @InputFacet(tableNameFacetConst)
        fun getDatabaseName(): String

        @ChildConcepts(DatabaseFieldConcept::class)
        fun getFields(): List<DatabaseFieldConcept>

    }

    @Concept(databaseFieldConceptConst)
    interface DatabaseFieldConcept {
        @InputFacet(fieldNameFacetConst)
        fun getFieldName(): String
        @InputFacet(fieldTypeFacetConst)
        fun getFieldType(): String
        @InputFacet(fieldLengthFacetConst)
        fun getFieldLength(): Int
    }

    private val schema = SchemaCreator.createSchemaFromSchemaDefinitionClass(DatabaseSchema::class.java)
    private fun createCollector(schema: SchemaAccess): ConceptDataCollector {
        return ConceptDataCollector(schema, validateConcept = false)
    }

    @Test
    fun `validate a valid singe root concept entry`() {
        // arrange
        val conceptDataCollector = createCollector(schema)
        val personTableId = ConceptIdentifier.of("Person")

        conceptDataCollector.newConceptData(
            conceptName = databaseTableConceptName,
            conceptIdentifier = personTableId,
            parentConceptIdentifier = null,
        ).addFacetValue(tableNameFacetName, "Person").attach()

        // act + assert
        assertForConceptDataValidator(personTableId, conceptDataCollector, schema)
    }

    @Test
    fun `validate a invalid concept entry with unknown concept name`() {
        // arrange
        val conceptDataCollector = createCollector(schema)
        val personTableId = ConceptIdentifier.of("Person")

        conceptDataCollector.newConceptData(
            conceptName = ConceptName.of("UnknownConcept"), // unknown concept
            conceptIdentifier = personTableId,
            parentConceptIdentifier = null,
        ).addFacetValue(tableNameFacetName, "Person").attach()

        // act + assert
        assertForConceptDataValidator(personTableId, conceptDataCollector, schema, ConceptNotKnownModelException::class)
    }


    @Test
    fun `validate a valid child concept entry`() {
        // arrange
        val conceptDataCollector = createCollector(schema)
        val personTableId = ConceptIdentifier.of("Person")

        conceptDataCollector.newConceptData(
            conceptName = databaseTableConceptName,
            conceptIdentifier = personTableId,
            parentConceptIdentifier = null,
        ).addFacetValue(tableNameFacetName, "Person").attach()

        val personFirstnameFieldId = ConceptIdentifier.of("Person_firstname")
        conceptDataCollector.newConceptData(
            conceptName = databaseTableFieldConceptName,
            conceptIdentifier = personFirstnameFieldId,
            parentConceptIdentifier = personTableId,
        )
            .addFacetValue(tableFieldNameFacetName, "firstname")
            .addFacetValue(tableFieldTypeFacetName, "VARCHAR")
            .addFacetValue(tableFieldLengthFacetName, 255)
            .attach()


        // act + assert
        assertForConceptDataValidator(personFirstnameFieldId, conceptDataCollector, schema)
    }


    @Test
    fun `validate a invalid root concept entry with unexpected parent concept`() {
        // arrange
        val conceptDataCollector = createCollector(schema)
        val personTableId = ConceptIdentifier.of("Person")

        conceptDataCollector.newConceptData(
            conceptName = databaseTableConceptName,
            conceptIdentifier = personTableId,
            parentConceptIdentifier = ConceptIdentifier.of("InvalidParentIdentifier"), // wrong concept
        ).addFacetValue(tableNameFacetName, "Person").attach()

        // act + assert
        assertForConceptDataValidator(personTableId, conceptDataCollector, schema, ConceptParentInvalidModelException::class)
    }


    @Test
    fun `validate a invalid child concept entry with expected but missing parent concept`() {
        // arrange
        val conceptDataCollector = createCollector(schema)

        val personFirstnameFieldId = ConceptIdentifier.of("Person_firstname")
        conceptDataCollector.newConceptData(
            conceptName = databaseTableFieldConceptName,
            conceptIdentifier = personFirstnameFieldId,
            parentConceptIdentifier = null, // parent concept missing
        )
            .addFacetValue(tableFieldNameFacetName, "firstname")
            .addFacetValue(tableFieldTypeFacetName, "VARCHAR")
            .addFacetValue(tableFieldLengthFacetName, 255)
            .attach()



        // act + assert
        assertForConceptDataValidator(personFirstnameFieldId, conceptDataCollector, schema, ConceptParentInvalidModelException::class)
    }

    @Test
    fun `validate a concept with missing facet`() {
        // arrange
        val conceptDataCollector = createCollector(schema)
        val personTableId = ConceptIdentifier.of("Person")

        conceptDataCollector.newConceptData(
            conceptName = databaseTableConceptName,
            conceptIdentifier = personTableId,
            parentConceptIdentifier = null,
        ).attach()// facet tableNameFacetName missing

        // act + assert
        assertForConceptDataValidator(personTableId, conceptDataCollector, schema, InvalidFacetConfigurationModelException::class)
    }

    @Test
    fun `validate a concept with wrong additional facet`() {
        // arrange
        val conceptDataCollector = createCollector(schema)

        val personTableId = ConceptIdentifier.of("Person")
        val personFirstnameFieldId = ConceptIdentifier.of("Person_firstname")
        conceptDataCollector.newConceptData(
            conceptName = databaseTableFieldConceptName,
            conceptIdentifier = personFirstnameFieldId,
            parentConceptIdentifier = personTableId,
        )
            .addFacetValue(tableFieldNameFacetName, "firstname")
            .addFacetValue(tableNameFacetName, "foobar") // this facet is not allowed in this concept
            .addFacetValue(tableFieldTypeFacetName, "VARCHAR")
            .addFacetValue(tableFieldLengthFacetName, 255)
            .attach()

        // act + assert
        assertForConceptDataValidator(personFirstnameFieldId, conceptDataCollector, schema, InvalidFacetConfigurationModelException::class)
    }

    @Test
    fun `validate a concept with wrong facet type`() {
        // arrange
        val conceptDataCollector = createCollector(schema)

        val personTableId = ConceptIdentifier.of("Person")
        val personFirstnameFieldId = ConceptIdentifier.of("Person_firstname")
        conceptDataCollector.newConceptData(
            conceptName = databaseTableFieldConceptName,
            conceptIdentifier = personFirstnameFieldId,
            parentConceptIdentifier = personTableId,
        )
            .addFacetValue(tableFieldNameFacetName, 23) // type field with wrong type
            .addFacetValue(tableFieldTypeFacetName,  "VARCHAR")
            .addFacetValue(tableFieldLengthFacetName,  255)
            .attach()

        // act + assert
        assertForConceptDataValidator(personFirstnameFieldId, conceptDataCollector, schema, InvalidFacetConfigurationModelException::class)
    }

    @Test
    fun `validate a concept with mandatory facet type but null value`() {
        // arrange
        val conceptDataCollector = createCollector(schema)

        val personTableId = ConceptIdentifier.of("Person")
        val personFirstnameFieldId = ConceptIdentifier.of("Person_firstname")
        conceptDataCollector.newConceptData(
            conceptName = databaseTableFieldConceptName,
            conceptIdentifier = personFirstnameFieldId,
            parentConceptIdentifier = personTableId,
        )
            .addFacetValue(tableFieldNameFacetName, null) // mandatory type with null value
            .addFacetValue(tableFieldTypeFacetName, "VARCHAR")
            .addFacetValue(tableFieldLengthFacetName,  255)
            .attach()

        // act + assert
        assertForConceptDataValidator(personFirstnameFieldId, conceptDataCollector, schema, InvalidFacetConfigurationModelException::class)
    }

    private fun assertForConceptDataValidator(conceptId: ConceptIdentifier, collector: ConceptDataCollector, schema: SchemaAccess, expectedExceptionType: KClass<out Throwable>? = null) {
        val conceptDataToValidate = entryByConceptIdentifier(conceptId, collector)
        if(expectedExceptionType == null) {
            ConceptDataValidator.validateSingleEntry(
                schema = schema,
                conceptData = conceptDataToValidate
            )
        } else {
            assertThrows(expectedExceptionType.java) {
                ConceptDataValidator.validateSingleEntry(
                    schema = schema,
                    conceptData = conceptDataToValidate
                )
            }
        }
    }

    private fun entryByConceptIdentifier(id: ConceptIdentifier, collector: ConceptDataCollector): ConceptData {
        return collector.conceptByConceptIdentifier(id)
    }

}