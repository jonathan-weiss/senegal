package ch.cassiamon.pluginapi


class NameOfMandatoryIntegerNumberFacet private constructor(name: String, isMandatoryFacetValue: Boolean)
    : FacetName(name, isMandatoryFacetValue) {

    companion object {
        fun of(name: String): NameOfMandatoryIntegerNumberFacet {
            return NameOfMandatoryIntegerNumberFacet(name, isMandatoryFacetValue = true)
        }
    }
}
