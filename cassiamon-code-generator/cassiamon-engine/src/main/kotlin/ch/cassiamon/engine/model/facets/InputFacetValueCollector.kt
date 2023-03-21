package ch.cassiamon.engine.model.facets

import ch.cassiamon.pluginapi.*
import ch.cassiamon.pluginapi.model.InputFacetValueAccess
import ch.cassiamon.pluginapi.model.facets.*

class InputFacetValueCollector: InputFacetValueAddition, InputFacetValueAccess {

    private val inputFacets: MutableMap<InputFacet<*>, InputFacetValue<*>> = mutableMapOf()

    override fun <T> facetValue(facet: InputFacet<T>): T {
        return when(facet) {
            is MandatoryTextInputFacet -> facetValueInternal(facet)
            is MandatoryNumberInputFacet -> facetValueInternal(facet)
            is OptionalNumberInputFacet -> facetValueInternal(facet)
            is MandatoryNumberInputAndTemplateFacet -> facetValueInternal(facet)
            is MandatoryTextInputAndTemplateFacet -> facetValueInternal(facet)
            is OptionalConceptIdentifierInputAndConceptNodeTemplateFacet -> facetValueInternal(facet)
            is MandatoryConceptIdentifierInputAndConceptNodeTemplateFacet -> facetValueInternal(facet)
        }
    }

    override fun <T> addFacetValue(facetWithValue: InputFacetValue<T>){
        inputFacets[facetWithValue.inputFacet] = facetWithValue
    }

    private inline fun <reified T> facetValueInternal(facet: InputFacet<T>): T {
        val facetValueWrapper: InputFacetValue<*>? = inputFacets[facet]

        val facetValue = facetValueWrapper?.facetValue
            ?: if(facet.isMandatoryInputFacetValue) {
                throw buildMandatoryFacetNotFoundException(facet)
            } else {
                return null as T
            }

        if(facetValue is T) {
            return facetValue
        }
        throw buildWrongFacetTypeException(facet)

    }

    override fun getFacetNames(): Set<FacetName> {
        return inputFacets.keys.map { it.facetName }.toSet()
    }

    private fun buildMandatoryFacetNotFoundException(facet: InputFacet<*>): Exception {
        throw IllegalStateException("Facet not found for facet name '${facet.facetName}'.")
    }

    private fun buildWrongFacetTypeException(facet: InputFacet<*>): Exception {
        throw IllegalStateException("Facet with facet name '${facet.facetName}' had a value with the wrong type.")
    }

}
