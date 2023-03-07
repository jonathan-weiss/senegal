package ch.cassiamon.pluginapi


class CalculatedOptionalTextFacetDescriptor private constructor(
    facetName: FacetName,
    isMandatoryFacetValue: Boolean,
): CalculatedFacetDescriptor<String?>(
    facetName = facetName,
    isMandatoryFacetValue = isMandatoryFacetValue,
) {

    companion object {
        fun of(name: String): CalculatedOptionalTextFacetDescriptor {
            return CalculatedOptionalTextFacetDescriptor(
                facetName = FacetName.of(name),
                isMandatoryFacetValue = true,
            )
        }
    }
}
