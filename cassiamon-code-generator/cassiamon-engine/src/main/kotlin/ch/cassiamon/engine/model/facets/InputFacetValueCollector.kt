package ch.cassiamon.engine.model.facets

import ch.cassiamon.pluginapi.*
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

            is InputAndTemplateFacetCombo<*, *> -> throw buildVirtualFacetInputNotAllowedException(facet)
            is MandatoryInputFacet -> throw buildVirtualFacetInputNotAllowedException(facet)
            is OptionalInputFacet -> throw buildVirtualFacetInputNotAllowedException(facet)
        }
    }

    override fun <T> addFacetValue(facet: InputFacet<T>, value: T){
        inputFacets[facet] = InputFacetValue(facet, value)
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

    private fun buildVirtualFacetInputNotAllowedException(facet: InputFacet<*>): Exception {
        return IllegalStateException("Can not use internal InputFacet classes (facet: ${facet.facetName})")
    }

    private fun buildWrongFacetTypeException(facet: InputFacet<*>): Exception {
        throw IllegalStateException("Facet with facet name '${facet.facetName}' had a value with the wrong type.")
    }

}
