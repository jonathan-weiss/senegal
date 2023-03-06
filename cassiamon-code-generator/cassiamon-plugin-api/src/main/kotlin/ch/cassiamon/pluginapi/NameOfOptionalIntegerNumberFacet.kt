package ch.cassiamon.pluginapi


class NameOfOptionalIntegerNumberFacet private constructor(name: String, isMandatoryFacetValue: Boolean)
    : FacetName(name, isMandatoryFacetValue) {

    companion object {
        fun of(name: String): NameOfOptionalIntegerNumberFacet {
            return NameOfOptionalIntegerNumberFacet(name, isMandatoryFacetValue = false)
        }
    }
}
