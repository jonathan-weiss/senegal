package ch.cassiamon.engine.model

import ch.cassiamon.engine.ProcessSession
import ch.cassiamon.engine.inputsource.ModelInputDataCollector
import ch.cassiamon.engine.schema.Schema
import ch.cassiamon.engine.schema.registration.RegistrationApiDefaultImpl
import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.model.ConceptIdentifier
import ch.cassiamon.pluginapi.model.ConceptModelNode
import ch.cassiamon.pluginapi.model.ConceptModelNodeCalculationData
import ch.cassiamon.pluginapi.model.exceptions.CircularDependencyOnTemplateFacetModelException
import ch.cassiamon.pluginapi.model.exceptions.InvalidTemplateFacetConfigurationModelException
import ch.cassiamon.pluginapi.model.exceptions.MissingFacetValueModelException
import ch.cassiamon.pluginapi.model.exceptions.ExceptionDuringTemplateFacetCalculationModelException
import ch.cassiamon.pluginapi.model.facets.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class ConceptModelGraphGraphCalculatorTemplateFacetTest {


    private val myConceptName = ConceptName.of("MyConcept")
    private val calculatedTemplateFacet = MandatoryTextTemplateFacet.of("MyCalculatedFacet")
    private val referencingCalculatedTemplateFacet = MandatoryTextTemplateFacet.of("ReferencingOnMyCalculatedFacet")
    private val optionalCalculatedTemplateFacet = OptionalNumberTemplateFacet.of("MyOptionalCalculatedFacet")
    private val optionalReferencingCalculatedTemplateFacet = OptionalNumberTemplateFacet.of("ReferencingMyOptionalCalculatedFacet")

    @Test
    fun `test simple successful calculated facet`() {

        // arrange + act
        val schema = createTestFixtureSchema  { "testString" }
        val myConceptModelNode = calculateConceptModelGraph(schema)

        // assert
        assertEquals("testString", myConceptModelNode.templateFacetValues.facetValue(calculatedTemplateFacet))
        assertEquals("Ref:testString", myConceptModelNode.templateFacetValues.facetValue(referencingCalculatedTemplateFacet))
    }

    @Test
    fun `test simple successful calculated optional facet`() {

        // arrange + act
        val schema = createTestFixtureSchemaForOptionalTemplateFacet  { 42.toLong() }
        val myConceptModelNode = calculateConceptModelGraph(schema)

        // assert
        assertEquals(42, myConceptModelNode.templateFacetValues.facetValue(optionalCalculatedTemplateFacet))
        assertEquals(84, myConceptModelNode.templateFacetValues.facetValue(optionalReferencingCalculatedTemplateFacet))
    }

    @Test
    fun `test successful calculated optional facet with null value`() {

        // arrange + act
        val schema = createTestFixtureSchemaForOptionalTemplateFacet  { null }
        val myConceptModelNode = calculateConceptModelGraph(schema)

        // assert
        assertNull(myConceptModelNode.templateFacetValues.facetValue(optionalCalculatedTemplateFacet))
        assertNull(myConceptModelNode.templateFacetValues.facetValue(optionalReferencingCalculatedTemplateFacet))
    }

    @Test
    fun `test calculated facet returning null for mandatory field`() {
        // assert
        Assertions.assertThrows(MissingFacetValueModelException::class.java) {
            // arrange + act
            val schema = createTestFixtureSchema<TextFacetKotlinType?> { null }
            calculateConceptModelGraph(schema)
        }
    }

    @Test
    fun `test calculated facet returning wrong type`() {
        // assert
        Assertions.assertThrows(InvalidTemplateFacetConfigurationModelException::class.java) {
            // arrange + act
            val schema = createTestFixtureSchema<NumberFacetKotlinType> { 42 }
            calculateConceptModelGraph(schema)
        }
    }

    @Test
    fun `test calculated facet throwing an exception`() {
        // assert
        Assertions.assertThrows(ExceptionDuringTemplateFacetCalculationModelException::class.java) {
            // arrange + act
            val schema = createTestFixtureSchema<TextFacetKotlinType?> { throw RuntimeException("Something went wrong") }
            calculateConceptModelGraph(schema)
        }
    }


    @Test
    fun `test circular dependency on self-referencing facet`() {
        // assert
        Assertions.assertThrows(CircularDependencyOnTemplateFacetModelException::class.java) {
            // arrange + act
            val schema = createTestFixtureSchema { data -> data.conceptModelNode.templateFacetValues.facetValue(calculatedTemplateFacet) }
            calculateConceptModelGraph(schema)
        }
    }

    @Test
    fun `test circular dependency between to calculated facets`() {
        // assert
        Assertions.assertThrows(CircularDependencyOnTemplateFacetModelException::class.java) {
            // arrange + act
            val schema = createTestFixtureSchema { data -> data.conceptModelNode.templateFacetValues.facetValue(referencingCalculatedTemplateFacet) }
            calculateConceptModelGraph(schema)
        }
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

    private fun <T> createTestFixtureSchema(calculatedFunction: (ConceptModelNodeCalculationData) -> T): Schema {
        val registrationApi = RegistrationApiDefaultImpl(ProcessSession())
        registrationApi.configureSchema {
            newRootConcept(conceptName = myConceptName) {
                addFacet(calculatedTemplateFacet, calculatedFunction)
                addFacet(referencingCalculatedTemplateFacet)
                {data -> "Ref:" + data.conceptModelNode.templateFacetValues.facetValue(calculatedTemplateFacet) }
            }
        }

        return registrationApi.provideSchema()
    }

    private fun <T> createTestFixtureSchemaForOptionalTemplateFacet(calculatedFunction: (ConceptModelNodeCalculationData) -> T?): Schema {
        val registrationApi = RegistrationApiDefaultImpl(ProcessSession())
        registrationApi.configureSchema {
            newRootConcept(conceptName = myConceptName) {
                addFacet(optionalCalculatedTemplateFacet, calculatedFunction)
                addFacet(optionalReferencingCalculatedTemplateFacet)
                {data -> data.conceptModelNode.templateFacetValues.facetValue(optionalCalculatedTemplateFacet)?.let { it + 42 } }
            }
        }

        return registrationApi.provideSchema()
    }

}
