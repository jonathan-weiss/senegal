package ch.cassiamon.pluginapi



class CalculatedOptionalIntegerNumberFacetDescriptor private constructor(
    facetName: FacetName,
    isMandatoryFacetValue: Boolean,
): CalculatedFacetDescriptor<Int?>(
    facetName = facetName,
    isMandatoryFacetValue = isMandatoryFacetValue,
) {

    companion object {
        fun of(name: String): CalculatedOptionalIntegerNumberFacetDescriptor {
            return CalculatedOptionalIntegerNumberFacetDescriptor(
                facetName = FacetName.of(name),
                isMandatoryFacetValue = true,
            )
        }
    }
}
