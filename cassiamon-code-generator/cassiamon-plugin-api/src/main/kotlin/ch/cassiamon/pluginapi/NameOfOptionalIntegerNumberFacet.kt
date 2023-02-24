package ch.cassiamon.pluginapi


class NameOfOptionalIntegerNumberFacet private constructor(name: String): FacetName(name) {

    companion object {
        fun of(name: String): NameOfOptionalIntegerNumberFacet {
            return NameOfOptionalIntegerNumberFacet(name)
        }
    }
}
