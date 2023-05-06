package ch.cassiamon.engine.schema.registration

import ch.cassiamon.engine.ProcessSession
import ch.cassiamon.engine.TestFixtures
import ch.cassiamon.pluginapi.schema.ConceptSchema
import ch.cassiamon.engine.schema.Schema
import ch.cassiamon.pluginapi.schema.InputFacetSchema
import ch.cassiamon.pluginapi.schema.TemplateFacetSchema
import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName
import ch.cassiamon.pluginapi.registration.exceptions.CircularConceptHierarchieFoundSchemaException
import ch.cassiamon.pluginapi.registration.exceptions.DuplicateConceptNameFoundSchemaException
import ch.cassiamon.pluginapi.registration.exceptions.DuplicateFacetNameFoundSchemaException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


class SchemaRegistrationApiTest {

    private val databaseTableConceptName = TestFixtures.databaseTableConceptName
    private val databaseTableFieldConceptName = TestFixtures.databaseTableFieldConceptName
    private val databaseTableFieldIndexConceptName = TestFixtures.databaseTableFieldIndexConceptName
    private val tableNameFacet = TestFixtures.tableNameFacet
    private val tableFieldNameFacet = TestFixtures.tableFieldNameFacet
    private val tableFieldTypeFacet = TestFixtures.tableFieldTypeFacet
    private val tableFieldLengthFacet = TestFixtures.tableFieldLengthFacet
    private val tableNameAndFieldNameFacet = TestFixtures.tableNameAndFieldNameFacet
    private val tableIndexNameFacet = TestFixtures.tableIndexNameFacet

    private val tableNameAndFieldNameFunction = TestFixtures.tableNameAndFieldNameFunction


    @Test
    fun `test with single concept`() {
        // arrange
        val registrationApi = getRegistrationApi()

        // act
        registrationApi.configureSchema {
            newRootConcept(conceptName = databaseTableConceptName) {
                addFacet(facet = tableNameFacet)
            }
        }

        // assert
        val schema = registrationApi.provideSchema()
        assertNotNull(schema)
        assertEquals(1, schema.numberOfConcepts())
        val firstConcept = findConcept(schema, databaseTableConceptName)
        assertEquals(this.databaseTableConceptName.name, firstConcept.conceptName.name)

        assertEquals(1, firstConcept.inputFacets.size)
        assertEquals(1, firstConcept.templateFacets.size)
        val firstInputFacet = findInputFacet(schema, databaseTableConceptName, tableNameFacet.facetName)
        val firstTemplateFacet = findTemplateFacet(schema, databaseTableConceptName, tableNameFacet.facetName)
        assertEquals(this.tableNameFacet, firstInputFacet.inputFacet)
        assertEquals(this.tableNameFacet, firstTemplateFacet.templateFacet)
    }

    @Test
    fun `test with nested concept`() {
        // arrange
        val registrationApi = getRegistrationApi()

        // act
        registrationApi.configureSchema {
            newRootConcept(conceptName = databaseTableConceptName) {
                addFacet(facet = tableNameFacet)

                newChildConcept(conceptName = databaseTableFieldConceptName) {
                    addFacet(facet = tableFieldNameFacet)
                    addFacet(facet = tableFieldTypeFacet) // TODO use enumeration as soon as available
                    addFacet(facet = tableFieldLengthFacet)
                    addFacet(facet = tableNameAndFieldNameFacet, facetCalculationFunction = tableNameAndFieldNameFunction)

                }
            }
        }

        // assert
        val schema = registrationApi.provideSchema()
        assertNotNull(schema)
        assertEquals(2, schema.numberOfConcepts())
        val tableConcept = findConcept(schema, databaseTableConceptName)
        val fieldConcept = findConcept(schema, databaseTableFieldConceptName )

        assertEquals(1, tableConcept.inputFacets.size)
        assertEquals(1, tableConcept.templateFacets.size)
        val tableNameInputFacet = findInputFacet(schema, databaseTableConceptName, tableNameFacet.facetName)
        assertEquals(this.tableNameFacet, tableNameInputFacet.inputFacet)
        val tableNameTemplateFacet = findTemplateFacet(schema, databaseTableConceptName, tableNameFacet.facetName)
        assertEquals(this.tableNameFacet, tableNameTemplateFacet.templateFacet)

        assertEquals(3, fieldConcept.inputFacets.size)
        assertEquals(4, fieldConcept.templateFacets.size)
        val tableNameAndFieldFacet = findTemplateFacet(schema, databaseTableFieldConceptName, tableNameAndFieldNameFacet.facetName)
        assertEquals(this.tableNameAndFieldNameFacet, tableNameAndFieldFacet.templateFacet)
    }

    @Test
    fun `test concept with duplicate facet names should throw an exception`() {
        // arrange
        val registrationApi = getRegistrationApi()


        // assert
        val thrown: DuplicateFacetNameFoundSchemaException = assertThrows(DuplicateFacetNameFoundSchemaException::class.java) {

            // act
            registrationApi.configureSchema {
                newRootConcept(conceptName = databaseTableConceptName) {
                    addFacet(facet = tableNameFacet)
                    addFacet(facet = tableNameFacet)
                }
            }

        }

        assertEquals(thrown.concept, databaseTableConceptName)
        assertEquals(thrown.facetName, tableNameFacet.facetName)
    }

    private fun findConcept(schema: Schema, conceptName: ConceptName): ConceptSchema {
        return schema.conceptByConceptName(conceptName)
    }

    private fun findInputFacet(schema: Schema, conceptName: ConceptName, facetName: FacetName): InputFacetSchema<*> {
        return findConcept(schema, conceptName).inputFacets.firstOrNull { it.inputFacet.facetName == facetName }
            ?: fail("No input facet found for $conceptName and $facetName.")
    }

    private fun findTemplateFacet(schema: Schema, conceptName: ConceptName, facetName: FacetName): TemplateFacetSchema<*> {
        return findConcept(schema, conceptName).templateFacets.firstOrNull { it.templateFacet.facetName == facetName }
            ?: fail("No template facet found for $conceptName and $facetName.")
    }

    @Test
    fun `test multiple concepts in hierarchie`() {
        // arrange
        val registrationApi = getRegistrationApi()

        // act
        registrationApi.configureSchema {
            newRootConcept(conceptName = databaseTableConceptName)
            {
                addFacet(facet = tableNameFacet)

                newChildConcept(conceptName = databaseTableFieldConceptName)
                {
                    addFacet(facet = tableFieldNameFacet)

                    newChildConcept(conceptName = databaseTableFieldIndexConceptName)
                    {
                        addFacet(facet = tableIndexNameFacet)
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
        val registrationApi = getRegistrationApi()

        // assert
        val thrown: CircularConceptHierarchieFoundSchemaException = assertThrows(CircularConceptHierarchieFoundSchemaException::class.java) {

            // act
            registrationApi.configureSchema {
                newRootConcept(databaseTableConceptName) {
                    addFacet(facet = tableFieldNameFacet)
                    newChildConcept(conceptName = databaseTableFieldConceptName)
                    {
                        addFacet(facet = tableFieldNameFacet)

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
        val registrationApi = getRegistrationApi()

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
        val registrationApi = getRegistrationApi()

        // assert
        val thrown: DuplicateConceptNameFoundSchemaException = assertThrows(DuplicateConceptNameFoundSchemaException::class.java) {

            // act
            registrationApi.configureSchema {
                newRootConcept(conceptName = databaseTableConceptName)
                {
                    addFacet(facet = tableNameFacet)
                    newChildConcept(conceptName = databaseTableFieldConceptName)
                    {
                        addFacet(facet = tableFieldNameFacet)

                        newChildConcept(conceptName = databaseTableConceptName)
                        {
                            addFacet(facet = tableIndexNameFacet)
                        }
                    }
                }
            }
        }

        assertEquals(databaseTableConceptName, thrown.concept)
    }

    private fun getRegistrationApi(): RegistrationApiDefaultImpl {
        return RegistrationApiDefaultImpl(ProcessSession())
    }

}
