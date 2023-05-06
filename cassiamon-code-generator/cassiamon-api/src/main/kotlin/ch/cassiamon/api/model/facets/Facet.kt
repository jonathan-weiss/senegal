package ch.cassiamon.api.model.facets

import ch.cassiamon.api.FacetName

sealed interface Facet {
    val facetName: FacetName
}
