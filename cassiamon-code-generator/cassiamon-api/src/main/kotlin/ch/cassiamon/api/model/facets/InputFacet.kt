package ch.cassiamon.api.model.facets

sealed interface InputFacet<out I>: Facet {

    val inputFacetType: FacetType<out I>

    val isMandatoryInputFacetValue: Boolean
        get() = inputFacetType.isMandatory

}
