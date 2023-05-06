package ch.cassiamon.api.model.facets

sealed interface TemplateFacet<out T>: Facet {

    val templateFacetType: FacetType<out T>
    val isMandatoryTemplateFacetValue: Boolean
        get() = templateFacetType.isMandatory

}
