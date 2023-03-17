package ch.cassiamon.pluginapi.model.facets

sealed interface InputFacet<out T>: Facet {
    val isMandatoryInputFacetValue: Boolean
        get() = true

}
