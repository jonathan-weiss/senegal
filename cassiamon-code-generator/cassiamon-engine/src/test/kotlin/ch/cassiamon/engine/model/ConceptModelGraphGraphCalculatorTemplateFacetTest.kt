package ch.cassiamon.engine.model

import ch.cassiamon.engine.ProcessSession
import ch.cassiamon.engine.inputsource.ModelInputDataCollector
import ch.cassiamon.engine.domain.Schema
import ch.cassiamon.engine.domain.registration.RegistrationApiDefaultImpl
import ch.cassiamon.api.ConceptName
import ch.cassiamon.api.FacetName
import ch.cassiamon.api.model.ConceptIdentifier
import ch.cassiamon.api.model.ConceptModelNode
import ch.cassiamon.api.model.exceptions.CircularDependencyOnTemplateFacetModelException
import ch.cassiamon.api.model.exceptions.InvalidTemplateFacetConfigurationModelException
import ch.cassiamon.api.model.exceptions.MissingFacetValueModelException
import ch.cassiamon.api.model.exceptions.ExceptionDuringTemplateFacetCalculationModelException
import ch.cassiamon.api.model.facets.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class ConceptModelGraphGraphCalculatorTemplateFacetTest {

    private val myConceptName = ConceptName.of("MyConcept")
    private val calculationFacetName = FacetName.of("MyCalculatedFacet")
    private val referencingFacetName = FacetName.of("MyReferencingFacet")

    @Test
    fun `test simple successful calculated facet`() {

        // arrange
        val calculatedTemplateFacet = MandatoryTextTemplateFacet.of(calculationFacetName.name) { "testString" }
        val referencingTemplateFacet = MandatoryTextTemplateFacet.of(referencingFacetName.name) { data -> "Ref:" + data.conceptModelNode.templateFacetValues.facetValue(calculatedTemplateFacet) }

        // act
        val schema = createTestFixtureSchema(calculatedTemplateFacet, referencingTemplateFacet)
        val myConceptModelNode = calculateConceptModelGraph(schema)

        // assert
        assertEquals("testString", myConceptModelNode.templateFacetValues.facetValue(calculatedTemplateFacet))
        assertEquals("Ref:testString", myConceptModelNode.templateFacetValues.facetValue(referencingTemplateFacet))
    }

    @Test
    fun `test simple successful calculated optional facet`() {

        // arrange
        val calculatedTemplateFacet = OptionalNumberTemplateFacet.of(calculationFacetName.name) { 42.toLong() }
        val referencingTemplateFacet = OptionalNumberTemplateFacet.of(referencingFacetName.name) { data -> data.conceptModelNode.templateFacetValues.facetValue(calculatedTemplateFacet)?.let { it + 42 } }

        // act
        val schema = createTestFixtureSchemaForOptionalTemplateFacet(calculatedTemplateFacet, referencingTemplateFacet)
        val myConceptModelNode = calculateConceptModelGraph(schema)

        // assert
        assertEquals(42, myConceptModelNode.templateFacetValues.facetValue(calculatedTemplateFacet))
        assertEquals(84, myConceptModelNode.templateFacetValues.facetValue(referencingTemplateFacet))
    }

    @Test
    fun `test successful calculated optional facet with null value`() {

        // arrange
        val calculatedTemplateFacet = OptionalNumberTemplateFacet.of(calculationFacetName.name) { null }
        val referencingTemplateFacet = OptionalNumberTemplateFacet.of(referencingFacetName.name) { data -> data.conceptModelNode.templateFacetValues.facetValue(calculatedTemplateFacet)?.let { it + 42 } }

        // act
        val schema = createTestFixtureSchemaForOptionalTemplateFacet(calculatedTemplateFacet, referencingTemplateFacet)
        val myConceptModelNode = calculateConceptModelGraph(schema)

        // assert
        assertNull(myConceptModelNode.templateFacetValues.facetValue(calculatedTemplateFacet))
        assertNull(myConceptModelNode.templateFacetValues.facetValue(referencingTemplateFacet))
    }

    @Test
    fun `test calculated facet returning null for mandatory field`() {
        // arrange
        val calculatedTemplateFacet = MandatoryTextTemplateFacet.of(calculationFacetName.name) { null as String }
        val referencingTemplateFacet = MandatoryTextTemplateFacet.of(referencingFacetName.name) { data -> "Ref:" + data.conceptModelNode.templateFacetValues.facetValue(calculatedTemplateFacet) }

        // assert
        Assertions.assertThrows(ExceptionDuringTemplateFacetCalculationModelException::class.java) {

            // act
            val schema = createTestFixtureSchema(calculatedTemplateFacet, referencingTemplateFacet)
            calculateConceptModelGraph(schema)
        }
    }

    @Test
    fun `test calculated facet returning wrong type`() {
        // arrange
        val calculatedTemplateFacet = MandatoryTextTemplateFacet.of(calculationFacetName.name) { 42 as String }
        val referencingTemplateFacet = MandatoryTextTemplateFacet.of(referencingFacetName.name) { data -> "Ref:" + data.conceptModelNode.templateFacetValues.facetValue(calculatedTemplateFacet) }

        // assert
        Assertions.assertThrows(ExceptionDuringTemplateFacetCalculationModelException::class.java) {

            // act
            val schema = createTestFixtureSchema(calculatedTemplateFacet, referencingTemplateFacet)
            calculateConceptModelGraph(schema)
        }
    }

    @Test
    fun `test calculated facet throwing an exception`() {
        // arrange
        val calculatedTemplateFacet = MandatoryTextTemplateFacet.of(calculationFacetName.name) { throw RuntimeException("something went wrong") }
        val referencingTemplateFacet = MandatoryTextTemplateFacet.of(referencingFacetName.name) { data -> "Ref:" + data.conceptModelNode.templateFacetValues.facetValue(calculatedTemplateFacet) }

        // assert
        Assertions.assertThrows(ExceptionDuringTemplateFacetCalculationModelException::class.java) {

            // act
            val schema = createTestFixtureSchema(calculatedTemplateFacet, referencingTemplateFacet)
            calculateConceptModelGraph(schema)
        }
    }


    @Test
    fun `test circular dependency on self-referencing facet`() {
        // test setup can not be reproduces. Assert throwing a CircularDependencyOnTemplateFacetModelException
    }

    @Test
    fun `test circular dependency between to calculated facets`() {
        // test setup can not be reproduces. Assert throwing a CircularDependencyOnTemplateFacetModelException
    }

    private fun calculateConceptModelGraph(schema: Schema): ConceptModelNode {
        val modelInputDataCollector = ModelInputDataCollector()

        val myConceptIdentifier = ConceptIdentifier.of("MyConceptIdentifier")
        modelInputDataCollector.newConceptData(myConceptName, myConceptIdentifier, null).attach()
        val conceptModelGraph = ConceptModelGraphCalculator.calculateConceptModelGraph(
            schema,
            modelInputDataCollector.provideModelInputData()
        )
        return conceptModelGraph.conceptModelNodeByConceptIdentifier(myConceptIdentifier)
    }

    private fun createTestFixtureSchema(calculatedTemplateFacet: MandatoryTextTemplateFacet, referencingTemplateFacet: MandatoryTextTemplateFacet): Schema {
        val registrationApi = RegistrationApiDefaultImpl(ProcessSession())
        registrationApi.configureSchema {
            newRootConcept(conceptName = myConceptName) {
                addFacet(calculatedTemplateFacet)
                addFacet(referencingTemplateFacet)
            }
        }

        return registrationApi.provideSchema()
    }

    private fun createTestFixtureSchemaForOptionalTemplateFacet(calculatedTemplateFacet: OptionalNumberTemplateFacet, referencingTemplateFacet: OptionalNumberTemplateFacet): Schema {
        val registrationApi = RegistrationApiDefaultImpl(ProcessSession())
        registrationApi.configureSchema {
            newRootConcept(conceptName = myConceptName) {
                addFacet(calculatedTemplateFacet)
                addFacet(referencingTemplateFacet)
            }
        }

        return registrationApi.provideSchema()
    }

}
