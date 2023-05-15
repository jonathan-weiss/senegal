package ch.cassiamon.api.model.facets

import ch.cassiamon.api.model.ConceptModelNodeCalculationData

sealed interface InputAndTemplateFacet<out I, out T>: InputFacet<I>, TemplateFacet<T> {
    override val facetCalculationFunction: (ConceptModelNodeCalculationData) -> T
}
