package ch.cassiamon.api.model.facets

import ch.cassiamon.api.model.ConceptModelNodeCalculationData

sealed interface InputAndTemplateFacet<out I, out T>: InputFacet<I>, TemplateFacet<T> {
    val facetCalculationFunction: (ConceptModelNodeCalculationData) -> T
}
