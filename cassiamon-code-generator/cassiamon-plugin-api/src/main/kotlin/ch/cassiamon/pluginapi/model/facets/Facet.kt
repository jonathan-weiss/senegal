package ch.cassiamon.pluginapi.model.facets

import ch.cassiamon.pluginapi.FacetName

sealed interface Facet {
    val facetName: FacetName
}
