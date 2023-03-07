package ch.cassiamon.engine.model.facets

import ch.cassiamon.pluginapi.ManualFacetDescriptor


data class ManualFacetValue<I, O>(
    val facetDescription: ManualFacetDescriptor<I, O>,
    val facetValue: I
)
