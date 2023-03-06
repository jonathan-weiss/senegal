package ch.cassiamon.pluginapi


class NameOfMandatoryTextFacet private constructor(name: String, isMandatoryFacetValue: Boolean)
    : FacetName(name, isMandatoryFacetValue) {

    companion object {
        fun of(name: String): NameOfMandatoryTextFacet {
            return NameOfMandatoryTextFacet(name, isMandatoryFacetValue = true)
        }
    }
}
