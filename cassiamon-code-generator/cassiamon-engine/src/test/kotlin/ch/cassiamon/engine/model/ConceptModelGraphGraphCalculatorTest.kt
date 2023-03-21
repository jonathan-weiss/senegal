package ch.cassiamon.engine.model

import ch.cassiamon.engine.TestFixtures
import ch.cassiamon.engine.inputsource.ModelInputDataCollector
import ch.cassiamon.pluginapi.model.ConceptIdentifier
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ConceptModelGraphGraphCalculatorTest {

    private val databaseTableConceptName = TestFixtures.databaseTableConceptName
    private val databaseTableFieldConceptName = TestFixtures.databaseTableFieldConceptName
    private val tableNameFacetName = TestFixtures.tableNameFacet
    private val tableFieldNameFacetName = TestFixtures.tableFieldNameFacet
    private val tableFieldTypeFacetName = TestFixtures.tableFieldTypeFacet
    private val tableFieldLengthFacetName = TestFixtures.tableFieldLengthFacet
    private val tableFieldForeignKeyConceptIdFacetName = TestFixtures.tableFieldForeignKeyConceptIdFacet
    private val tableNameAndFieldNameFacetName = TestFixtures.tableNameAndFieldNameFacet


    @Test
    fun `test simple successful graph`() {

        // arrange
        val schema = TestFixtures.createTestFixtureSchema()
        val modelInputDataCollector = ModelInputDataCollector()

        val personTableId = ConceptIdentifier.of("PERSON")
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
        assertEquals("PERSON", personTemplateModelNode.templateFacetValues.facetValue(tableNameFacetName))
    }

    @Test
    fun `test a graph with valid hierarchical and reference dependencies`() {
        // arrange
        val schema = TestFixtures.createTestFixtureSchema()
        val modelInputDataCollector = ModelInputDataCollector()

        val addressTableId = ConceptIdentifier.of("ADDRESS")
        val addressStreetId = ConceptIdentifier.of("ADDRESS_STREET")
        val addressPersonForeignKeyFieldId = ConceptIdentifier.of("ADDRESS_FK_PERSON_ID")
        val personTableId = ConceptIdentifier.of("PERSON")
        val personFirstnameFieldId = ConceptIdentifier.of("PERSON_FIRSTNAME")

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
        val referencedConcept = fkField.templateFacetValues.facetValue(tableFieldForeignKeyConceptIdFacetName)
        assertNotNull(referencedConcept)
        assertEquals(personTableId, referencedConcept?.conceptIdentifier)
    }

    @Test
    fun `test a graph with calculated methods`() {
        // arrange
        val schema = TestFixtures.createTestFixtureSchema()
        val modelInputDataCollector = ModelInputDataCollector()

        val personTableId = ConceptIdentifier.of("PERSON")
        val personFirstnameFieldId = ConceptIdentifier.of("FIRSTNAME")

        modelInputDataCollector.attachTable(table = personTableId)
        modelInputDataCollector.attachVarcharTableField(table = personTableId, field = personFirstnameFieldId)

        // act
        val conceptModelGraph = ConceptModelGraphCalculator.calculateConceptModelGraph(
            schema,
            modelInputDataCollector.provideModelInputData()
        )

        // assert
        assertNotNull(conceptModelGraph)
        val personFirstnameField = conceptModelGraph.conceptModelNodeByConceptIdentifier(personFirstnameFieldId)
        assertNotNull(personFirstnameField)
        assertEquals("PERSON.FIRSTNAME", personFirstnameField.templateFacetValues.facetValue(tableNameAndFieldNameFacetName))
    }



    @Test
    fun `calculate a graph with valid mutual dependency`() {
        // arrange
        val schema = TestFixtures.createTestFixtureSchema()
        val modelInputDataCollector = ModelInputDataCollector()

        val addressTableId = ConceptIdentifier.of("ADDRESS")
        val addressStreetId = ConceptIdentifier.of("ADDRESS_STREET")
        val addressToPersonForeignKeyFieldId = ConceptIdentifier.of("ADDRESS_FK_PERSON_ID")
        val personTableId = ConceptIdentifier.of("PERSON")
        val personFirstnameFieldId = ConceptIdentifier.of("PERSON_FIRSTNAME")
        val personToAddressForeignKeyFieldId = ConceptIdentifier.of("PERSON_FK_ADDRESS_ID")

        modelInputDataCollector.attachTable(table = addressTableId)
        modelInputDataCollector.attachVarcharTableField(table = addressTableId, field = addressStreetId)
        modelInputDataCollector.attachUuidTableField(table = addressTableId, field = addressToPersonForeignKeyFieldId, foreignKeyToTable = personTableId)
        modelInputDataCollector.attachTable(table = personTableId)
        modelInputDataCollector.attachVarcharTableField(table = personTableId, field = personFirstnameFieldId)
        modelInputDataCollector.attachUuidTableField(table = personTableId, field = personToAddressForeignKeyFieldId, foreignKeyToTable = addressTableId)

        // act
        val conceptModelGraph = ConceptModelGraphCalculator.calculateConceptModelGraph(
            schema,
            modelInputDataCollector.provideModelInputData()
        )

        // assert
        assertNotNull(conceptModelGraph)

        val personTable = conceptModelGraph.conceptModelNodeByConceptIdentifier(personTableId)
        val addressTable = conceptModelGraph.conceptModelNodeByConceptIdentifier(addressTableId)

        val personFkField = conceptModelGraph.conceptModelNodeByConceptIdentifier(personToAddressForeignKeyFieldId)
        val addressFkField = conceptModelGraph.conceptModelNodeByConceptIdentifier(addressToPersonForeignKeyFieldId)

        val referenceToAddressTable = personFkField.templateFacetValues.facetValue(tableFieldForeignKeyConceptIdFacetName)
        val referenceToPersonTable = addressFkField.templateFacetValues.facetValue(tableFieldForeignKeyConceptIdFacetName)

        assertNotNull(referenceToPersonTable)
        assertNotNull(referenceToAddressTable)

        assertEquals(personTable, referenceToPersonTable)
        assertEquals(addressTable, referenceToAddressTable)

        assertEquals(personTableId, referenceToPersonTable?.conceptIdentifier)
        assertEquals(addressTableId, referenceToAddressTable?.conceptIdentifier)


    }

    private fun ModelInputDataCollector.attachTable(table: ConceptIdentifier) {
        this.newConceptData(
            conceptName = databaseTableConceptName,
            conceptIdentifier = table,
            parentConceptIdentifier = null,
        ).addFacetValue(tableNameFacetName.facetValue(table.code)).attach()

    }

    private fun ModelInputDataCollector.attachUuidTableField(table: ConceptIdentifier,
                                                             field: ConceptIdentifier,
                                                             foreignKeyToTable: ConceptIdentifier? = null,) {
        this.newConceptData(
            conceptName = databaseTableFieldConceptName,
            conceptIdentifier = field,
            parentConceptIdentifier = table,
        )
            .addFacetValue(tableFieldNameFacetName.facetValue(fieldNameFromIdentifier(field)))
            .addFacetValue(tableFieldTypeFacetName.facetValue( "UUID"))
            .addFacetValue(tableFieldLengthFacetName.facetValue(128))
            .addFacetValue(tableFieldForeignKeyConceptIdFacetName.facetValue(foreignKeyToTable))
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
            .addFacetValue(tableFieldNameFacetName.facetValue(fieldNameFromIdentifier(field)))
            .addFacetValue(tableFieldTypeFacetName.facetValue("VARCHAR"))
            .addFacetValue(tableFieldLengthFacetName.facetValue(255))
            .attach()
    }
}
