package ch.cassiamon.engine.model

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
import ch.cassiamon.pluginapi.model.facets.MandatoryTextTemplateFacet
import ch.cassiamon.pluginapi.model.facets.NumberFacetKotlinType
import ch.cassiamon.pluginapi.model.facets.TextFacetKotlinType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ConceptModelGraphGraphCalculatorTemplateFacetTest {


    private val myConceptName = ConceptName.of("MyConcept")
    private val calculatedTemplateFacet = MandatoryTextTemplateFacet.of("MyCalculatedFacet")
    private val referencingCalculatedTemplateFacet = MandatoryTextTemplateFacet.of("ReferencingOnMyCalculatedFacet")

    @Test
    fun `test simple successful calculated facet`() {

        // arrange + act
        val myConceptModelNode = calculateConceptModelGraph { "testString" }

        // assert
        assertEquals("testString", myConceptModelNode.templateFacetValues.facetValue(calculatedTemplateFacet))
        assertEquals("testString", myConceptModelNode.templateFacetValues.facetValue(referencingCalculatedTemplateFacet))
    }

    @Test
    fun `test calculated facet returning null for mandatory field`() {
        // assert
        Assertions.assertThrows(MissingFacetValueModelException::class.java) {
            // arrange + act
            calculateConceptModelGraph<TextFacetKotlinType?> { null }
        }
    }

    @Test
    fun `test calculated facet returning wrong type`() {
        // assert
        Assertions.assertThrows(InvalidTemplateFacetConfigurationModelException::class.java) {
            // arrange + act
            calculateConceptModelGraph<NumberFacetKotlinType> { 42 }
        }
    }

    @Test
    fun `test calculated facet throwing an exception`() {
        // assert
        Assertions.assertThrows(ExceptionDuringTemplateFacetCalculationModelException::class.java) {
            // arrange + act
            calculateConceptModelGraph<TextFacetKotlinType?> { throw RuntimeException("Something went wrong") }
        }
    }


    @Test
    fun `test circular dependency on self-referencing facet`() {
        // assert
        Assertions.assertThrows(CircularDependencyOnTemplateFacetModelException::class.java) {
            // arrange + act
            calculateConceptModelGraph { data -> data.conceptModelNode.templateFacetValues.facetValue(calculatedTemplateFacet) }
        }
    }

    @Test
    fun `test circular dependency between to calculated facets`() {
        // assert
        Assertions.assertThrows(CircularDependencyOnTemplateFacetModelException::class.java) {
            // arrange + act
            calculateConceptModelGraph { data -> data.conceptModelNode.templateFacetValues.facetValue(referencingCalculatedTemplateFacet) }
        }
    }

    private fun <T> calculateConceptModelGraph(calculatedFunction: (ConceptModelNodeCalculationData) -> T): ConceptModelNode {
        val schema = createTestFixtureSchema(calculatedFunction)
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
        val registrationApi = RegistrationApiDefaultImpl()
        registrationApi.configureSchema {
            newRootConcept(conceptName = myConceptName) {
                addFacet(calculatedTemplateFacet, calculatedFunction)
                addFacet(referencingCalculatedTemplateFacet) {data -> data.conceptModelNode.templateFacetValues.facetValue(calculatedTemplateFacet) }
            }
        }

        return registrationApi.provideSchema()
    }

}
