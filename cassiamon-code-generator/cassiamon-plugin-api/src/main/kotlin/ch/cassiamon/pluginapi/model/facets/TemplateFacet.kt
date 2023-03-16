package ch.cassiamon.pluginapi.model.facets

sealed interface TemplateFacet<out T>: Facet {
    val isMandatoryFacetValue: Boolean
        get() = true // TODO this is only a temporary solution
}
