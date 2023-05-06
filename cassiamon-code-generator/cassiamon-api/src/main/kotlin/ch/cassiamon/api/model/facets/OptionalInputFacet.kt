package ch.cassiamon.api.model.facets

sealed interface OptionalInputFacet<out I: Any?>: InputFacet<I> {

    override val inputFacetType: OptionalFacetType<out I>


}
