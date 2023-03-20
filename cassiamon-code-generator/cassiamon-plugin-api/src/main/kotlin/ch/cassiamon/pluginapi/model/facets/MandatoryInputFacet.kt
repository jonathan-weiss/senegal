package ch.cassiamon.pluginapi.model.facets

sealed interface MandatoryInputFacet<out I: Any>: InputFacet<I> {

    override val inputFacetType: MandatoryFacetType<out I>


}
