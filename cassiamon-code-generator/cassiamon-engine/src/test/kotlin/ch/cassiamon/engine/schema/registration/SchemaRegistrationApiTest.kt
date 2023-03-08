package ch.cassiamon.engine.schema.registration

import ch.cassiamon.engine.TestFixtures
import ch.cassiamon.engine.schema.facets.FacetSchema
import ch.cassiamon.engine.schema.ConceptSchema
import ch.cassiamon.engine.schema.facets.FacetType
import ch.cassiamon.engine.schema.Schema
import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName
import ch.cassiamon.pluginapi.registration.exceptions.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test


class SchemaRegistrationApiTest {

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
        registrationApi.configureSchema {
            newRootConcept(conceptName = databaseTableConceptName) {
                addTextFacet(facetDescriptor = tableNameFacetName)
            }
        }

        // assert
        val schema = registrationApi.provideSchema()
        assertNotNull(schema)
        assertEquals(1, schema.numberOfConcepts())
        val firstConcept = findConcept(schema, databaseTableConceptName)
        assertEquals(this.databaseTableConceptName.name, firstConcept.conceptName.name)

        assertEquals(1, firstConcept.facets.size)
        val firstFacet = findFacet(schema, databaseTableConceptName, tableNameFacetName.facetName)
        assertEquals(this.tableNameFacetName.facetName, firstFacet.facetDescriptor.facetName)
        assertEquals(FacetType.TEXT, firstFacet.facetType)
    }

    @Test
    fun `test with nested concept`() {
        // arrange
        val registrationApi = RegistrationApiDefaultImpl()

        // act
        registrationApi.configureSchema {
            newRootConcept(conceptName = databaseTableConceptName) {
                addTextFacet(facetDescriptor = tableNameFacetName)

                newChildConcept(conceptName = databaseTableFieldConceptName) {
                    addTextFacet(tableFieldNameFacetName)
                    addTextFacet(tableFieldTypeFacetName) // TODO use enumeration as soon as available
                    addIntegerNumberFacet(tableFieldLengthFacetName)
                    addCalculatedTextFacet(
                        facetDescriptor = tableNameAndFieldNameFacetName
                    ) { node -> "TODO write <TableName>.<FieldName>" } // TODO write simple code example as soon as nodes have properties

                }
            }
        }

        // assert
        val schema = registrationApi.provideSchema()
        assertNotNull(schema)
        assertEquals(2, schema.numberOfConcepts())
        val tableConcept = findConcept(schema, databaseTableConceptName)
        val fieldConcept = findConcept(schema, databaseTableFieldConceptName )

        assertEquals(1, tableConcept.facets.size)
        val tableNameFacet = findFacet(schema, databaseTableConceptName, tableNameFacetName.facetName)
        assertEquals(this.tableNameFacetName.facetName.name, tableNameFacet.facetDescriptor.facetName.name)
        assertEquals(FacetType.TEXT, tableNameFacet.facetType)

        assertEquals(4, fieldConcept.facets.size)
        val tableNameAndFieldFacet = findFacet(schema, databaseTableFieldConceptName, tableNameAndFieldNameFacetName.facetName)
        assertEquals(this.tableNameAndFieldNameFacetName.facetName.name, tableNameAndFieldFacet.facetDescriptor.facetName.name)
        assertEquals(FacetType.TEXT, tableNameAndFieldFacet.facetType)
    }

    @Test
    fun `test concept with duplicate facet names should throw an exception`() {
        // arrange
        val registrationApi = RegistrationApiDefaultImpl()


        // assert
        val thrown: DuplicateFacetNameFoundSchemaException = assertThrows(DuplicateFacetNameFoundSchemaException::class.java) {

            // act
            registrationApi.configureSchema {
                newRootConcept(conceptName = databaseTableConceptName) {
                    addTextFacet(facetDescriptor = tableNameFacetName)
                    addTextFacet(facetDescriptor = tableNameFacetName)
                }
            }

        }

        assertEquals(thrown.concept, databaseTableConceptName)
        assertEquals(thrown.facetName, tableNameFacetName)
    }

    @Test
    @Disabled
    fun `test concept with unknown facet dependencies should throw an exception`() {
        // arrange
        val registrationApi = RegistrationApiDefaultImpl()


        // assert
//        val thrown: FacetDependencyNotFoundSchemaException = assertThrows(FacetDependencyNotFoundSchemaException::class.java) {
//
//            // act
//            registrationApi.configureSchema {
//                newRootConcept(conceptName = databaseTableFieldConceptName) {
//                    addTextFacet(facetName = tableFieldNameFacetName)
//                    addTextFacet(
//                        facetName = tableFieldTypeFacetName,
//                        dependingOnFacets = setOf(tableFieldLengthFacetName, tableFieldNameFacetName)
//                    )
//                    addIntegerNumberFacet(facetName = tableFieldLengthFacetName)
//                }
//            }
//
//        }
//
//        assertEquals(databaseTableFieldConceptName, thrown.concept)
//        assertEquals(setOf(tableFieldLengthFacetName), thrown.facets)
    }

    @Test
    @Disabled
    fun `test concept with cyclic dependent facets should throw an exception`() {
        // arrange
        val registrationApi = RegistrationApiDefaultImpl()


        // assert
//        val thrown: FacetDependencyNotFoundSchemaException = assertThrows(FacetDependencyNotFoundSchemaException::class.java) {
//
//            // act
//            registrationApi.configureSchema {
//                newRootConcept(conceptName = databaseTableFieldConceptName) {
//                    addTextFacet(facetName = tableFieldNameFacetName, dependingOnFacets = setOf(tableFieldTypeFacetName))
//                    addTextFacet(facetName = tableFieldTypeFacetName, dependingOnFacets = setOf(tableFieldNameFacetName))
//                }
//            }
//
//        }
//
//        assertEquals(databaseTableFieldConceptName, thrown.concept)
    }

    @Test
    @Disabled
    fun `test concept with multiple dependent facets`() {
        // arrange
        val registrationApi = RegistrationApiDefaultImpl()


//        // act
//        registrationApi.configureSchema {
//            newRootConcept(conceptName = databaseTableFieldConceptName) {
//                addTextFacet(facetName = tableFieldNameFacetName)
//                addTextFacet(facetName = tableFieldTypeFacetName)
//                addIntegerNumberFacet(facetName = tableFieldLengthFacetName)
//
//                addTextFacet(
//                    facetName = tableNameAndFieldNameFacetName,
//                )
//            }
//        }
//        val schema = registrationApi.provideSchema()
//
//        // assert
//        assertEquals(databaseTableFieldConceptName, findConcept(schema, databaseTableFieldConceptName).conceptName)
//        assertEquals(setOf(tableFieldNameFacetName, tableFieldTypeFacetName, tableFieldLengthFacetName),
//            findFacet(schema, databaseTableFieldConceptName, tableNameAndFieldNameFacetName).facetDependencies)
    }

    private fun findConcept(schema: Schema, conceptName: ConceptName): ConceptSchema {
        return schema.conceptByConceptName(conceptName)
    }

    private fun findFacet(schema: Schema, conceptName: ConceptName, facetName: FacetName): FacetSchema<*> {
        return findConcept(schema, conceptName).facets.firstOrNull { it.facetDescriptor.facetName == facetName }
            ?: fail("No facet found for $conceptName and $facetName.")
    }

    @Test
    fun `test multiple concepts in hierarchie`() {
        // arrange
        val registrationApi = RegistrationApiDefaultImpl()

        // act
        registrationApi.configureSchema {
            newRootConcept(conceptName = databaseTableConceptName)
            {
                addTextFacet(facetDescriptor = tableNameFacetName)

                newChildConcept(conceptName = databaseTableFieldConceptName)
                {
                    addTextFacet(facetDescriptor = tableFieldNameFacetName)

                    newChildConcept(conceptName = databaseTableFieldIndexConceptName)
                    {
                        addTextFacet(facetDescriptor = tableIndexNameFacetName)
                    }

                }

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
    fun `test root concept inside a child concept throws exception`() {
        // arrange
        val registrationApi = RegistrationApiDefaultImpl()

        // assert
        val thrown: CircularConceptHierarchieFoundSchemaException = assertThrows(CircularConceptHierarchieFoundSchemaException::class.java) {

            // act
            registrationApi.configureSchema {
                newRootConcept(databaseTableConceptName) {
                    addTextFacet(facetDescriptor = tableFieldNameFacetName)
                    newChildConcept(conceptName = databaseTableFieldConceptName)
                    {
                        addTextFacet(facetDescriptor = tableFieldNameFacetName)

                        newRootConcept(databaseTableConceptName) {

                        }
                    }

                }
            }
        }

        assertEquals(databaseTableConceptName, thrown.concept)
        assertEquals(databaseTableFieldConceptName, thrown.parentConcept)
    }

    @Test
    fun `test concept with duplicate concept name throws exception`() {
        // arrange
        val registrationApi = RegistrationApiDefaultImpl()

        // assert
        val thrown: DuplicateConceptNameFoundSchemaException = assertThrows(DuplicateConceptNameFoundSchemaException::class.java) {

            // act
            registrationApi.configureSchema {
                newRootConcept(conceptName = databaseTableConceptName) {}
                newRootConcept(conceptName = databaseTableConceptName) {}
            }
        }

        assertEquals(databaseTableConceptName, thrown.concept)
    }

    @Test
    fun `test multiple concepts in cyclic hierarchy throws exception`() {
        // arrange
        val registrationApi = RegistrationApiDefaultImpl()

        // assert
        val thrown: DuplicateConceptNameFoundSchemaException = assertThrows(DuplicateConceptNameFoundSchemaException::class.java) {

            // act
            registrationApi.configureSchema {
                newRootConcept(conceptName = databaseTableConceptName)
                {
                    addTextFacet(facetDescriptor = tableNameFacetName)
                    newChildConcept(conceptName = databaseTableFieldConceptName)
                    {
                        addTextFacet(facetDescriptor = tableFieldNameFacetName)

                        newChildConcept(conceptName = databaseTableConceptName)
                        {
                            addTextFacet(facetDescriptor = tableIndexNameFacetName)
                        }
                    }
                }
            }
        }

        assertEquals(databaseTableConceptName, thrown.concept)
    }

}
