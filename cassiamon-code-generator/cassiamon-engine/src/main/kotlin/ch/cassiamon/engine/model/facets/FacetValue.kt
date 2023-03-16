package ch.cassiamon.engine.model.facets

import ch.cassiamon.pluginapi.FacetDescriptor


data class FacetValue<O>(
    val facetDescription: FacetDescriptor<O>,
    val facetValue: O
)
