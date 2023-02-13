package ch.cassiamon.engine.schema.registration

import ch.cassiamon.engine.TestFixtures
import ch.cassiamon.engine.schema.types.FacetType
import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName
import ch.cassiamon.pluginapi.ConceptNode
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class RegistrationTest {

    private val databaseTableConceptName = TestFixtures.databaseTableConceptName
    private val databaseTableFieldConceptName = TestFixtures.databaseTableFieldConceptName
    private val tableNameFacetName = TestFixtures.tableNameFacetName
    private val tableFieldNameFacetName = TestFixtures.tableFieldNameFacetName
    private val tableFieldTypeFacetName = TestFixtures.tableFieldTypeFacetName
    private val tableFieldLengthFacetName = TestFixtures.tableFieldLengthFacetName
    private val tableNameAndFieldNameFacetName = TestFixtures.tableNameAndFieldNameFacetName


    @Test
    fun `test with single concept`() {
        // arrange
        val registrationApi = RegistrationApiDefaultImpl()

        // act
        registrationApi.configure {
            newRootConcept(conceptName = databaseTableConceptName) {
                addTextFacet(facetName = tableNameFacetName)
            }
        }

        // assert
        val schema = registrationApi.provideSchema()
        assertNotNull(schema)
        assertEquals(1, schema.concepts.size)
        val firstConcept = schema.concepts.first()
        assertEquals(this.databaseTableConceptName.name, firstConcept.conceptName.name)

        assertEquals(1, firstConcept.facets.size)
        val firstFacet = firstConcept.facets.first()
        assertEquals(this.tableNameFacetName.name, firstFacet.facetName.name)
        assertEquals(FacetType.TEXT, firstFacet.facetType)
    }

    @Test
    fun `test with nested concept`() {
        // arrange
        val registrationApi = RegistrationApiDefaultImpl()

        // act
        registrationApi.configure {
            newRootConcept(conceptName = databaseTableConceptName) {
                addTextFacet(facetName = tableNameFacetName) { _, value -> value.uppercase() }

                newChildConcept(conceptName = databaseTableFieldConceptName, parentConceptName = databaseTableConceptName) {
                    addTextFacet(tableFieldNameFacetName) { _, value -> value.uppercase() }
                    addTextFacet(tableFieldTypeFacetName) // TODO use enumeration as soon as available
                    addIntegerNumberFacet(tableFieldLengthFacetName, setOf(tableFieldTypeFacetName))
                    addCalculatedTextFacet(
                        facetName = tableNameAndFieldNameFacetName,
                        dependingOnFacets = setOf(tableFieldNameFacetName)
                        ) { node -> "TODO write <TableName>.<FieldName>" } // TODO write simple code example as soon as nodes have properties

                }

            }
        }

        // assert
        val schema = registrationApi.provideSchema()
        assertNotNull(schema)
        assertEquals(2, schema.concepts.size)
        val tableConcept = schema.concepts.first { it.conceptName == this.databaseTableConceptName }
        val fieldConcept = schema.concepts.first { it.conceptName == this.databaseTableFieldConceptName }

        assertEquals(1, tableConcept.facets.size)
        val tableNameFacet = tableConcept.facets.first()
        assertEquals(this.tableNameFacetName.name, tableNameFacet.facetName.name)
        assertEquals(FacetType.TEXT, tableNameFacet.facetType)

        assertEquals(4, fieldConcept.facets.size)
        val tableNameAndFieldFacet = fieldConcept.facets.last()
        assertEquals(this.tableNameAndFieldNameFacetName.name, tableNameAndFieldFacet.facetName.name)
        assertEquals(FacetType.TEXT, tableNameAndFieldFacet.facetType)
        assertEquals(1, tableNameAndFieldFacet.facetDependencies.size)
        assertEquals(this.tableFieldNameFacetName, tableNameAndFieldFacet.facetDependencies.first())

    }

    @Test
    fun registrationApi() {

        val dbTableConcept = ConceptName.of("DbTable")
        val dbFieldConcept = ConceptName.of("DbField")
        val dbTableNameFacet = FacetName.of("TableName")
        val dbTableMaxNumberOfRowsFacet = FacetName.of("MaxNumberOfRows")
        val dbFieldNameFacet = FacetName.of("FieldName")

        val registrationApi = RegistrationApiDefaultImpl()
        registrationApi.configure {
            println("In Registration")
            newRootConcept(dbTableConcept) {
                println("In ConceptRegistration $dbTableConcept")
                addTextFacet(dbTableNameFacet, setOf(dbTableNameFacet)) { conceptNode: ConceptNode, value: String -> value }
                addIntegerNumberFacet(dbTableMaxNumberOfRowsFacet)
            }
            newChildConcept(dbFieldConcept, dbTableConcept) {
                addTextFacet(dbFieldNameFacet)
            }

        }

        assertNotNull(registrationApi)

    }
}
