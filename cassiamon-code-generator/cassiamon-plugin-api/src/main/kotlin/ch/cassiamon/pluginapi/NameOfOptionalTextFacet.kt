package ch.cassiamon.pluginapi


class NameOfOptionalTextFacet private constructor(name: String, isMandatoryFacetValue: Boolean)
    : FacetName(name, isMandatoryFacetValue) {

    companion object {
        fun of(name: String): NameOfOptionalTextFacet {
            return NameOfOptionalTextFacet(name, isMandatoryFacetValue = false)
        }
    }
}
