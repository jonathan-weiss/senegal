package ch.cassiamon.engine.model.graph

import ch.cassiamon.engine.TestFixtures
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class ModelGraphCreatorTest {

    private val databaseTableConceptName = TestFixtures.databaseTableConceptName
    private val databaseTableFieldConceptName = TestFixtures.databaseTableFieldConceptName
    private val tableNameFacetName = TestFixtures.tableNameFacetName
    private val tableFieldNameFacetName = TestFixtures.tableFieldNameFacetName
    private val tableFieldTypeFacetName = TestFixtures.tableFieldTypeFacetName
    private val tableFieldLengthFacetName = TestFixtures.tableFieldLengthFacetName
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
}
