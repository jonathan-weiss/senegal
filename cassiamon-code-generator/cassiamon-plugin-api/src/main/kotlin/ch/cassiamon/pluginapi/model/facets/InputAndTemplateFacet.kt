package ch.cassiamon.pluginapi.model.facets

import ch.cassiamon.pluginapi.model.ConceptModelNodeCalculationData

sealed interface InputAndTemplateFacet<out I, out T>: InputFacet<I>, TemplateFacet<T> {
    val facetCalculationFunction: (ConceptModelNodeCalculationData) -> T
}
