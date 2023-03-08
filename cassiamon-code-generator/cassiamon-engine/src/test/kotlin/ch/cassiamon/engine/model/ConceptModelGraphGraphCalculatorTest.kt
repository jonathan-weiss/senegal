package ch.cassiamon.engine.model

import ch.cassiamon.engine.TestFixtures
import ch.cassiamon.engine.inputsource.ModelInputDataCollector
import ch.cassiamon.pluginapi.model.ConceptIdentifier
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class ConceptModelGraphGraphCalculatorTest {

    private val databaseTableConceptName = TestFixtures.databaseTableConceptName
    private val databaseTableFieldConceptName = TestFixtures.databaseTableFieldConceptName
    private val tableNameFacetName = TestFixtures.tableNameFacetName
    private val tableFieldNameFacetName = TestFixtures.tableFieldNameFacetName
    private val tableFieldTypeFacetName = TestFixtures.tableFieldTypeFacetName
    private val tableFieldLengthFacetName = TestFixtures.tableFieldLengthFacetName
    private val tableFieldForeignKeyConceptIdFacetName = TestFixtures.tableFieldForeignKeyConceptIdFacetName
    private val tableNameAndFieldNameFacetName = TestFixtures.tableNameAndFieldNameFacetName


    @Test
    fun `test simple successful graph`() {

        // arrange
        val schema = TestFixtures.createTestFixtureSchema()
        val modelInputDataCollector = ModelInputDataCollector()

        val personTableId = ConceptIdentifier.of("Person")
        modelInputDataCollector.attachTable(table = personTableId)

        // act
        val conceptModelGraph = ConceptModelGraphCalculator.calculateConceptModelGraph(
            schema,
            modelInputDataCollector.provideModelInputData()
        )

        // assert
        assertNotNull(conceptModelGraph)
        assertEquals(1, conceptModelGraph.conceptModelNodesByConceptName(databaseTableConceptName).size)
        val personTemplateModelNode = conceptModelGraph.conceptModelNodesByConceptName(databaseTableConceptName).first()
        assertEquals(personTableId, personTemplateModelNode.conceptIdentifier)
        assertEquals("Person", personTemplateModelNode.facetValues.asString(tableNameFacetName))
    }

    @Disabled
    @Test
    fun `test a graph with valid hierarchical and reference dependencies`() {
        // arrange
        val schema = TestFixtures.createTestFixtureSchema()
        val modelInputDataCollector = ModelInputDataCollector()

        val addressTableId = ConceptIdentifier.of("Address")
        val addressStreetId = ConceptIdentifier.of("Address_street")
        val addressPersonForeignKeyFieldId = ConceptIdentifier.of("Address_fkPerson")
        val personTableId = ConceptIdentifier.of("Person")
        val personFirstnameFieldId = ConceptIdentifier.of("Person_firstname")
        val addressPersonForeignKeyPointerFieldId = ConceptIdentifier.of("Address_fkPersonPointer")

        modelInputDataCollector.attachTable(table = addressTableId)
        modelInputDataCollector.attachVarcharTableField(table = addressTableId, field = addressStreetId)
        modelInputDataCollector.attachUuidTableField(table = addressTableId, field = addressPersonForeignKeyFieldId, foreignKeyToTable = personTableId)
        modelInputDataCollector.attachTable(table = personTableId)
        modelInputDataCollector.attachVarcharTableField(table = personTableId, field = personFirstnameFieldId)

        // act
        val conceptModelGraph = ConceptModelGraphCalculator.calculateConceptModelGraph(
            schema,
            modelInputDataCollector.provideModelInputData()
        )

        // assert
        assertNotNull(conceptModelGraph)

        assertEquals(2, conceptModelGraph.conceptModelNodesByConceptName(databaseTableConceptName).size)

        val fkField = conceptModelGraph.conceptModelNodeByConceptIdentifier(addressPersonForeignKeyFieldId)
        val referencedConcept = fkField.facetValues.asReferencedConceptModelNode(tableFieldForeignKeyConceptIdFacetName)
        assertNotNull(referencedConcept)
        assertEquals(personTableId, referencedConcept?.conceptIdentifier)
    }


    @Disabled
    @Test
    fun `calculate a graph with invalid cyclic hierarchical dependency`() {
        // arrange
        val schema = TestFixtures.createTestFixtureSchema()
        val modelInputDataCollector = ModelInputDataCollector()

        val addressTableId = ConceptIdentifier.of("Address")
        val addressStreetFieldId = ConceptIdentifier.of("Address_street")
        val addressToAddressForeignKeyFieldId = ConceptIdentifier.of("Address_fk")
        val addressToAddressForeignKeyFieldPointerId = ConceptIdentifier.of("Address_to_Address_fk")
        modelInputDataCollector.attachTable(table = addressTableId)
        modelInputDataCollector.attachVarcharTableField(table = addressTableId, field = addressStreetFieldId)
        modelInputDataCollector.attachUuidTableField(table = addressTableId, field = addressToAddressForeignKeyFieldId)
        // TODO add here a circular dependency

        // act
        val templateModelGraph = ConceptModelGraphCalculator.calculateConceptModelGraph(
            schema,
            modelInputDataCollector.provideModelInputData()
        )

        // assert
        assertNotNull(templateModelGraph)

    }

    private fun ModelInputDataCollector.attachTable(table: ConceptIdentifier) {
        this.newConceptData(
            conceptName = databaseTableConceptName,
            conceptIdentifier = table,
            parentConceptIdentifier = null,
        ).addFacetValue(tableNameFacetName, table.code).attach()

    }

    private fun ModelInputDataCollector.attachUuidTableField(table: ConceptIdentifier,
                                                             field: ConceptIdentifier,
                                                             foreignKeyToTable: ConceptIdentifier? = null,) {
        this.newConceptData(
            conceptName = databaseTableFieldConceptName,
            conceptIdentifier = field,
            parentConceptIdentifier = table,
        )
            .addFacetValue(tableFieldNameFacetName, fieldNameFromIdentifier(field))
            .addFacetValue(tableFieldTypeFacetName, "UUID")
            .addFacetValue(tableFieldLengthFacetName, 128)
            .addFacetValue(tableFieldForeignKeyConceptIdFacetName, foreignKeyToTable)
            .attach()
    }

    private fun fieldNameFromIdentifier(field: ConceptIdentifier): String {
        return field.code.split("_").last()
    }

    private fun ModelInputDataCollector.attachVarcharTableField(
        table: ConceptIdentifier,
        field: ConceptIdentifier) {
        this.newConceptData(
            conceptName = databaseTableFieldConceptName,
            conceptIdentifier = field,
            parentConceptIdentifier = table,
        )
            .addFacetValue(tableFieldNameFacetName, fieldNameFromIdentifier(field))
            .addFacetValue(tableFieldTypeFacetName, "VARCHAR")
            .addFacetValue(tableFieldLengthFacetName, 255)
            .attach()
    }
}
