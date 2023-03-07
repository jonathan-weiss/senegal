package ch.cassiamon.pluginapi



sealed class ManualFacetDescriptor<I, O> constructor(
    facetName: FacetName,
    isMandatoryFacetValue: Boolean
): FacetDescriptor<O>(
    facetName = facetName,
    isMandatoryFacetValue = isMandatoryFacetValue,
    isManualFacetValue = true,
)
