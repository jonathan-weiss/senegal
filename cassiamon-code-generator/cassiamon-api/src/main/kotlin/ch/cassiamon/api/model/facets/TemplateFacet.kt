package ch.cassiamon.api.model.facets

import ch.cassiamon.api.model.ConceptModelNodeCalculationData

sealed interface TemplateFacet<out T>: Facet {

    val templateFacetType: FacetType<out T>
    val isMandatoryTemplateFacetValue: Boolean
        get() = templateFacetType.isMandatory

    val facetCalculationFunction: (ConceptModelNodeCalculationData) -> T
}
