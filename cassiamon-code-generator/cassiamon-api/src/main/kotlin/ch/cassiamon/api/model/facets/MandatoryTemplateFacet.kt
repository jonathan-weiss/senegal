package ch.cassiamon.api.model.facets

sealed interface MandatoryTemplateFacet<out T: Any>: TemplateFacet<T> {

    override val templateFacetType: MandatoryFacetType<out T>
}
