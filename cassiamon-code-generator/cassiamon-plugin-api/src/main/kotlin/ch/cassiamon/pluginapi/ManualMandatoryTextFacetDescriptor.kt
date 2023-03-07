package ch.cassiamon.pluginapi


class ManualMandatoryTextFacetDescriptor private constructor(facetName: FacetName, isMandatoryFacetValue: Boolean)
    : ManualFacetDescriptor<String, String>(facetName, isMandatoryFacetValue) {

    companion object {
        fun of(name: String): ManualMandatoryTextFacetDescriptor {
            return ManualMandatoryTextFacetDescriptor(
                facetName = FacetName.of(name),
                isMandatoryFacetValue = true,
            )
        }
    }
}
