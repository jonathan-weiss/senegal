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
    private val tableNameFacetDescriptor = TestFixtures.tableNameFacetDescriptor
    private val tableFieldNameFacetDescriptor = TestFixtures.tableFieldNameFacetDescriptor
    private val tableFieldTypeFacetDescriptor = TestFixtures.tableFieldTypeFacetDescriptor
    private val tableFieldLengthFacetDescriptor = TestFixtures.tableFieldLengthFacetDescriptor
    private val tableNameAndFieldNameFacetDescriptor = TestFixtures.tableNameAndFieldNameFacetDescriptor
    private val tableIndexNameFacetDescriptor = TestFixtures.tableIndexNameFacetDescriptor


    @Test
    fun `test with single concept`() {
        // arrange
        val registrationApi = RegistrationApiDefaultImpl()

        // act
        registrationApi.configureSchema {
            newRootConcept(conceptName = databaseTableConceptName) {
                addFacet(facetDescriptor = tableNameFacetDescriptor)
            }
        }

        // assert
        val schema = registrationApi.provideSchema()
        assertNotNull(schema)
        assertEquals(1, schema.numberOfConcepts())
        val firstConcept = findConcept(schema, databaseTableConceptName)
        assertEquals(this.databaseTableConceptName.name, firstConcept.conceptName.name)

        assertEquals(1, firstConcept.facets.size)
        val firstFacet = findFacet(schema, databaseTableConceptName, tableNameFacetDescriptor.facetName)
        assertEquals(this.tableNameFacetDescriptor.facetName, firstFacet.facetDescriptor.facetName)
        assertEquals(FacetType.TEXT, firstFacet.facetType)
    }

    @Test
    fun `test with nested concept`() {
        // arrange
        val registrationApi = RegistrationApiDefaultImpl()

        // act
        registrationApi.configureSchema {
            newRootConcept(conceptName = databaseTableConceptName) {
                addFacet(facetDescriptor = tableNameFacetDescriptor)

                newChildConcept(conceptName = databaseTableFieldConceptName) {
                    addFacet(tableFieldNameFacetDescriptor)
                    addFacet(tableFieldTypeFacetDescriptor) // TODO use enumeration as soon as available
                    addFacet(tableFieldLengthFacetDescriptor)
                    addFacet(
                        facetDescriptor = tableNameAndFieldNameFacetDescriptor
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
        val tableNameFacet = findFacet(schema, databaseTableConceptName, tableNameFacetDescriptor.facetName)
        assertEquals(this.tableNameFacetDescriptor.facetName.name, tableNameFacet.facetDescriptor.facetName.name)
        assertEquals(FacetType.TEXT, tableNameFacet.facetType)

        assertEquals(4, fieldConcept.facets.size)
        val tableNameAndFieldFacet = findFacet(schema, databaseTableFieldConceptName, tableNameAndFieldNameFacetDescriptor.facetName)
        assertEquals(this.tableNameAndFieldNameFacetDescriptor.facetName.name, tableNameAndFieldFacet.facetDescriptor.facetName.name)
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
                    addFacet(facetDescriptor = tableNameFacetDescriptor)
                    addFacet(facetDescriptor = tableNameFacetDescriptor)
                }
            }

        }

        assertEquals(thrown.concept, databaseTableConceptName)
        assertEquals(thrown.facetName, tableNameFacetDescriptor.facetName)
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
                addFacet(facetDescriptor = tableNameFacetDescriptor)

                newChildConcept(conceptName = databaseTableFieldConceptName)
                {
                    addFacet(facetDescriptor = tableFieldNameFacetDescriptor)

                    newChildConcept(conceptName = databaseTableFieldIndexConceptName)
                    {
                        addFacet(facetDescriptor = tableIndexNameFacetDescriptor)
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
                    addFacet(facetDescriptor = tableFieldNameFacetDescriptor)
                    newChildConcept(conceptName = databaseTableFieldConceptName)
                    {
                        addFacet(facetDescriptor = tableFieldNameFacetDescriptor)

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
                    addFacet(facetDescriptor = tableNameFacetDescriptor)
                    newChildConcept(conceptName = databaseTableFieldConceptName)
                    {
                        addFacet(facetDescriptor = tableFieldNameFacetDescriptor)

                        newChildConcept(conceptName = databaseTableConceptName)
                        {
                            addFacet(facetDescriptor = tableIndexNameFacetDescriptor)
                        }
                    }
                }
            }
        }

        assertEquals(databaseTableConceptName, thrown.concept)
    }

}
