package ch.cassiamon.pluginapi


class ManualMandatoryIntegerNumberFacetDescriptor private constructor(facetName: FacetName, isMandatoryFacetValue: Boolean)
    : ManualFacetDescriptor<Int, Int>(facetName, isMandatoryFacetValue) {

    companion object {
        fun of(name: String): ManualMandatoryIntegerNumberFacetDescriptor {
            return ManualMandatoryIntegerNumberFacetDescriptor(
                facetName = FacetName.of(name),
                isMandatoryFacetValue = true,
            )
        }
    }
}
