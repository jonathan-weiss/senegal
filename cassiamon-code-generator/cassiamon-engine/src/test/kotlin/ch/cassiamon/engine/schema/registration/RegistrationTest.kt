package ch.cassiamon.engine.schema.registration

import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName
import ch.cassiamon.pluginapi.registration.GraphNode
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class RegistrationTest {

    private val dbTableConcept = ConceptName.of("DbTable")
    private val dbTableNameFacet = FacetName.of("TableName")

    @Test
    fun `test with single concept`() {
        // arrange
        val registrationApi = RegistrationApiDefaultImpl()

        // act
        registrationApi.configure {
            newRootConcept(dbTableConcept) {
                newTextFacet(dbTableNameFacet) {
                    addFacetFunction { _, value -> value.uppercase()}
                }
            }
        }

        // assert
        assertNotNull(registrationApi)
        assertEquals(1, registrationApi.concepts.size)
        val firstConcept = registrationApi.concepts.first()
        assertEquals(dbTableConcept.name, firstConcept.conceptName.name)

        assertEquals(1, firstConcept.facets.size)
        val firstFacet = firstConcept.facets.first()
        assertEquals(dbTableNameFacet.name, firstFacet.facetName.name)
        assertEquals(FacetType.TEXT, firstFacet.facetType)
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
                newTextFacet(dbTableNameFacet) {
                    println("In TextFacetRegistration $dbTableNameFacet")
                    addFacetFunction { graphNode: GraphNode, value: String -> value }
                    addFacetFunction { graphNode: GraphNode, value: String -> value }
                    addFacetDependency(dbTableConcept, dbTableNameFacet)
                }
                newIntegerNumberFacet(dbTableMaxNumberOfRowsFacet) {}
            }
            newChildConcept(dbFieldConcept, dbTableConcept) {
                newTextFacet(dbFieldNameFacet) {

                }
            }

        }

        assertNotNull(registrationApi)

    }
}
