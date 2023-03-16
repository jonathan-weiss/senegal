package ch.cassiamon.engine.model.facets

import ch.cassiamon.pluginapi.*
import ch.cassiamon.pluginapi.model.facets.InputFacet
import ch.cassiamon.pluginapi.model.facets.MandatoryNumberInputFacet
import ch.cassiamon.pluginapi.model.facets.MandatoryTextInputFacet
import ch.cassiamon.pluginapi.model.facets.OptionalNumberInputFacet

class InputFacetValueCollector: InputFacetValueAddition, InputFacetValueAccess {

    private val inputFacets: MutableMap<InputFacet<*>, InputFacetValue<*>> = mutableMapOf()
    private val facetNames: MutableSet<FacetName> = mutableSetOf()


    override fun <T> facetValue(facet: InputFacet<T>): T {
        return when(facet) {
            is MandatoryTextInputFacet -> facetValueInternal(facet)
            is MandatoryNumberInputFacet -> facetValueInternal(facet)
            is OptionalNumberInputFacet -> facetValueInternal(facet)
            else -> throw IllegalArgumentException("wrong type")
        }
    }

    override fun <T> addFacetValue(facet: InputFacet<T>, value: T){
        inputFacets[facet] = InputFacetValue(facet, value)
        facetNames.add(facet.facetName)
    }


    private inline fun <reified T> facetValueInternal(facet: InputFacet<T>): T {
        val facetValueWrapper = inputFacets[facet]
            ?: throw buildFacetNotFoundException(facet)
        // TODO handle this specially
        if(facetValueWrapper.facetValue is T) {
            return facetValueWrapper.facetValue
        }
        throw IllegalStateException("wrong type")

    }

    override fun getFacetNames(): Set<FacetName> {
        return facetNames.toSet()
    }

    private fun buildFacetNotFoundException(facet: InputFacet<*>): Exception {
        throw IllegalStateException("Facet not found for facet name '${facet.facetName}'.")
    }


}
