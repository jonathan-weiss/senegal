package ch.cassiamon.pluginapi


class NameOfMandatoryIntegerNumberFacet private constructor(name: String): FacetName(name) {

    companion object {
        fun of(name: String): NameOfMandatoryIntegerNumberFacet {
            return NameOfMandatoryIntegerNumberFacet(name)
        }
    }
}
