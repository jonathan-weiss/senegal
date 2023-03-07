package ch.cassiamon.pluginapi



sealed class CalculatedFacetDescriptor<O> constructor(
    facetName: FacetName,
    isMandatoryFacetValue: Boolean,
): FacetDescriptor<O>(
    facetName = facetName,
    isMandatoryFacetValue = isMandatoryFacetValue,
    isManualFacetValue = false,
)
