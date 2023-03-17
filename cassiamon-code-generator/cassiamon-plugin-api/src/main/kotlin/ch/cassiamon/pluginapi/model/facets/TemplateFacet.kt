package ch.cassiamon.pluginapi.model.facets

sealed interface TemplateFacet<out T>: Facet {
    val isMandatoryTemplateFacetValue: Boolean
        get() = true
}
