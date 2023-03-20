package ch.cassiamon.engine.model

import ch.cassiamon.engine.TestFixtures
import ch.cassiamon.engine.inputsource.ModelInputDataCollector
import ch.cassiamon.pluginapi.model.ConceptIdentifier
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class SecondConceptModelGraphGraphCalculatorTest {

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



        modelInputDataCollector
            .newConceptData(databaseTableConceptName, personTableId, null)
            .addFacetValue(tableNameFacetName, "null")
            .addFacetValue(tableFieldForeignKeyConceptIdFacetName, null)
            .attach()



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

}
