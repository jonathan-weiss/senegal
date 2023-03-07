package ch.cassiamon.pluginapi


class ManualOptionalIntegerNumberFacetDescriptor private constructor(facetName: FacetName, isMandatoryFacetValue: Boolean)
    : ManualFacetDescriptor<Int?, Int?>(facetName, isMandatoryFacetValue) {

    companion object {
        fun of(name: String): ManualOptionalIntegerNumberFacetDescriptor {
            return ManualOptionalIntegerNumberFacetDescriptor(
                facetName = FacetName.of(name),
                isMandatoryFacetValue = false,
            )
        }
    }
}
