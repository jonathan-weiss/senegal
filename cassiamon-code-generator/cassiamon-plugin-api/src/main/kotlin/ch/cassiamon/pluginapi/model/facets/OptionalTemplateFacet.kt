package ch.cassiamon.pluginapi.model.facets

sealed interface OptionalTemplateFacet<out T: Any?>: TemplateFacet<T> {

    override val templateFacetType: OptionalFacetType<out T>
}
