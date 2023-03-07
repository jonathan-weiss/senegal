package ch.cassiamon.pluginapi


class ManualOptionalTextFacetDescriptor private constructor(facetName: FacetName, isMandatoryFacetValue: Boolean)
    : ManualFacetDescriptor<String?, String?>(facetName, isMandatoryFacetValue) {

    companion object {
        fun of(name: String): ManualOptionalTextFacetDescriptor {
            return ManualOptionalTextFacetDescriptor(
                facetName = FacetName.of(name),
                isMandatoryFacetValue = false,
            )
        }
    }
}
