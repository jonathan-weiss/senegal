package ch.cassiamon.engine.schema.registration

import ch.cassiamon.engine.TestFixtures
import ch.cassiamon.engine.schema.types.Concept
import ch.cassiamon.engine.schema.types.Facet
import ch.cassiamon.engine.schema.types.FacetType
import ch.cassiamon.engine.schema.types.Schema
import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName
import ch.cassiamon.pluginapi.registration.exceptions.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


class RegistrationApiTest {

    private val databaseTableConceptName = TestFixtures.databaseTableConceptName
    private val databaseTableFieldConceptName = TestFixtures.databaseTableFieldConceptName
    private val databaseTableFieldIndexConceptName = TestFixtures.databaseTableFieldIndexConceptName
    private val tableNameFacetName = TestFixtures.tableNameFacetName
    private val tableFieldNameFacetName = TestFixtures.tableFieldNameFacetName
    private val tableFieldTypeFacetName = TestFixtures.tableFieldTypeFacetName
    private val tableFieldLengthFacetName = TestFixtures.tableFieldLengthFacetName
    private val tableNameAndFieldNameFacetName = TestFixtures.tableNameAndFieldNameFacetName
    private val tableIndexNameFacetName = TestFixtures.tableIndexNameFacetName


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
        val firstConcept = findConcept(schema, databaseTableConceptName)
        assertEquals(this.databaseTableConceptName.name, firstConcept.conceptName.name)

        assertEquals(1, firstConcept.facets.size)
        val firstFacet = findFacet(schema, databaseTableConceptName, tableNameFacetName)
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
            }
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

        // assert
        val schema = registrationApi.provideSchema()
        assertNotNull(schema)
        assertEquals(2, schema.concepts.size)
        val tableConcept = findConcept(schema, databaseTableConceptName)
        val fieldConcept = findConcept(schema, databaseTableFieldConceptName )

        assertEquals(1, tableConcept.facets.size)
        val tableNameFacet = findFacet(schema, databaseTableConceptName, tableNameFacetName)
        assertEquals(this.tableNameFacetName.name, tableNameFacet.facetName.name)
        assertEquals(FacetType.TEXT, tableNameFacet.facetType)

        assertEquals(4, fieldConcept.facets.size)
        val tableNameAndFieldFacet = findFacet(schema, databaseTableFieldConceptName, tableNameAndFieldNameFacetName)
        assertEquals(this.tableNameAndFieldNameFacetName.name, tableNameAndFieldFacet.facetName.name)
        assertEquals(FacetType.TEXT, tableNameAndFieldFacet.facetType)
        assertEquals(1, tableNameAndFieldFacet.facetDependencies.size)
        assertEquals(this.tableFieldNameFacetName, tableNameAndFieldFacet.facetDependencies.first())

    }

    @Test
    fun `test concept with duplicate facet names should throw an exception`() {
        // arrange
        val registrationApi = RegistrationApiDefaultImpl()


        // assert
        val thrown: DuplicateFacetNameFoundSchemaException = assertThrows(DuplicateFacetNameFoundSchemaException::class.java) {

            // act
            registrationApi.configure {
                newRootConcept(conceptName = databaseTableConceptName) {
                    addTextFacet(facetName = tableNameFacetName)
                    addTextFacet(facetName = tableNameFacetName)
                }
            }

        }

        assertEquals(thrown.concept, databaseTableConceptName)
        assertEquals(thrown.facet, tableNameFacetName)
    }

    @Test
    fun `test concept with unknown facet dependencies should throw an exception`() {
        // arrange
        val registrationApi = RegistrationApiDefaultImpl()


        // assert
        val thrown: FacetDependencyNotFoundSchemaException = assertThrows(FacetDependencyNotFoundSchemaException::class.java) {

            // act
            registrationApi.configure {
                newRootConcept(conceptName = databaseTableFieldConceptName) {
                    addTextFacet(facetName = tableFieldNameFacetName)
                    addTextFacet(
                        facetName = tableFieldTypeFacetName,
                        dependingOnFacets = setOf(tableFieldLengthFacetName, tableFieldNameFacetName)
                    )
                    addIntegerNumberFacet(facetName = tableFieldLengthFacetName)
                }
            }

        }

        assertEquals(databaseTableFieldConceptName, thrown.concept)
        assertEquals(setOf(tableFieldLengthFacetName), thrown.facets)
    }

    @Test
    fun `test concept with cyclic dependent facets should throw an exception`() {
        // arrange
        val registrationApi = RegistrationApiDefaultImpl()


        // assert
        val thrown: FacetDependencyNotFoundSchemaException = assertThrows(FacetDependencyNotFoundSchemaException::class.java) {

            // act
            registrationApi.configure {
                newRootConcept(conceptName = databaseTableFieldConceptName) {
                    addTextFacet(facetName = tableFieldNameFacetName, dependingOnFacets = setOf(tableFieldTypeFacetName))
                    addTextFacet(facetName = tableFieldTypeFacetName, dependingOnFacets = setOf(tableFieldNameFacetName))
                }
            }

        }

        assertEquals(databaseTableFieldConceptName, thrown.concept)
    }

    @Test
    fun `test concept with multiple dependent facets`() {
        // arrange
        val registrationApi = RegistrationApiDefaultImpl()


        // act
        registrationApi.configure {
            newRootConcept(conceptName = databaseTableFieldConceptName) {
                addTextFacet(facetName = tableFieldNameFacetName)
                addTextFacet(facetName = tableFieldTypeFacetName, dependingOnFacets = setOf(tableFieldNameFacetName))
                addIntegerNumberFacet(facetName = tableFieldLengthFacetName, dependingOnFacets = setOf(tableFieldTypeFacetName))

                addTextFacet(
                    facetName = tableNameAndFieldNameFacetName,
                    dependingOnFacets = setOf(tableFieldNameFacetName, tableFieldTypeFacetName, tableFieldLengthFacetName)
                )
            }
        }
        val schema = registrationApi.provideSchema()

        // assert
        assertEquals(databaseTableFieldConceptName, findConcept(schema, databaseTableFieldConceptName).conceptName)
        assertEquals(setOf(tableFieldNameFacetName, tableFieldTypeFacetName, tableFieldLengthFacetName),
            findFacet(schema, databaseTableFieldConceptName, tableNameAndFieldNameFacetName).facetDependencies)
    }

    private fun findConcept(schema: Schema, conceptName: ConceptName): Concept {
        return schema.concepts.firstOrNull { it.conceptName == conceptName }
            ?: fail("No concept found with name $conceptName.")
    }

    private fun findFacet(schema: Schema, conceptName: ConceptName, facetName: FacetName): Facet {
        return findConcept(schema, conceptName).facets.firstOrNull { it.facetName == facetName }
            ?: fail("No facet found for $conceptName and $facetName.")
    }

    @Test
    fun `test multiple concepts in hierarchie`() {
        // arrange
        val registrationApi = RegistrationApiDefaultImpl()

        // act
        registrationApi.configure {
            newRootConcept(conceptName = databaseTableConceptName)
            {
                addTextFacet(facetName = tableNameFacetName)
            }
            newChildConcept(conceptName = databaseTableFieldConceptName,
                parentConceptName = databaseTableConceptName)
            {
                addTextFacet(facetName = tableFieldNameFacetName)
            }
            newChildConcept(conceptName = databaseTableFieldIndexConceptName,
                parentConceptName = databaseTableFieldConceptName)
            {
                addTextFacet(facetName = tableIndexNameFacetName)
            }
        }
        val schema = registrationApi.provideSchema()

        // assert
        val databaseTableConcept = findConcept(schema, databaseTableConceptName)
        val databaseTableFieldConcept = findConcept(schema, databaseTableFieldConceptName)
        val databaseTableFieldIndexConcept = findConcept(schema, databaseTableFieldIndexConceptName)

        assertNull(databaseTableConcept.parentConceptName)
        assertEquals(databaseTableFieldConcept.parentConceptName, databaseTableConceptName)
        assertEquals(databaseTableFieldIndexConcept.parentConceptName, databaseTableFieldConceptName)

    }

    @Test
    fun `test concept with unknown concept as parent throws exception`() {
        // arrange
        val registrationApi = RegistrationApiDefaultImpl()

        // assert
        val thrown: UnknownParentConceptFoundSchemaException = assertThrows(UnknownParentConceptFoundSchemaException::class.java) {

            // act
            registrationApi.configure {
                newRootConcept(databaseTableConceptName) {
                    addTextFacet(facetName = tableFieldNameFacetName)
                }
                newChildConcept(
                    conceptName = databaseTableFieldConceptName,
                    parentConceptName = databaseTableFieldIndexConceptName
                )
                {
                    addTextFacet(facetName = tableFieldNameFacetName)
                }
            }
        }

        assertEquals(databaseTableFieldConceptName, thrown.concept)
        assertEquals(databaseTableFieldIndexConceptName, thrown.parentConcept)
    }

    @Test
    fun `test concept with duplicate concept name throws exception`() {
        // arrange
        val registrationApi = RegistrationApiDefaultImpl()

        // assert
        val thrown: DuplicateConceptNameFoundSchemaException = assertThrows(DuplicateConceptNameFoundSchemaException::class.java) {

            // act
            registrationApi.configure {
                newRootConcept(conceptName = databaseTableConceptName) {}
                newRootConcept(conceptName = databaseTableConceptName) {}
            }
        }

        assertEquals(databaseTableConceptName, thrown.concept)
    }

    @Test
    fun `test concept with itself as parent throws exception`() {
        // arrange
        val registrationApi = RegistrationApiDefaultImpl()

        // assert
        val thrown: CircularConceptHierarchieFoundSchemaException = assertThrows(CircularConceptHierarchieFoundSchemaException::class.java) {

            // act
            registrationApi.configure {
                newChildConcept(
                    conceptName = databaseTableFieldConceptName,
                    parentConceptName = databaseTableFieldConceptName
                )
                {
                    addTextFacet(facetName = tableFieldNameFacetName)
                }
            }
        }

        assertEquals(databaseTableFieldConceptName, thrown.concept)
        assertEquals(databaseTableFieldConceptName, thrown.parentConcept)
    }

    @Test
    fun `test multiple concepts in cyclic hierarchy throws exception`() {
        // arrange
        val registrationApi = RegistrationApiDefaultImpl()

        // assert
        val thrown: DuplicateConceptNameFoundSchemaException = assertThrows(DuplicateConceptNameFoundSchemaException::class.java) {

            // act
            registrationApi.configure {
                newRootConcept(conceptName = databaseTableConceptName)
                {
                    addTextFacet(facetName = tableNameFacetName)
                }
                newChildConcept(conceptName = databaseTableFieldConceptName,
                    parentConceptName = databaseTableConceptName)
                {
                    addTextFacet(facetName = tableFieldNameFacetName)
                }
                newChildConcept(conceptName = databaseTableConceptName,
                    parentConceptName = databaseTableFieldConceptName)
                {
                    addTextFacet(facetName = tableIndexNameFacetName)
                }
            }
        }

        assertEquals(databaseTableConceptName, thrown.concept)
    }

}
