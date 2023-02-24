package ch.cassiamon.pluginapi


class NameOfOptionalTextFacet private constructor(name: String): FacetName(name) {

    companion object {
        fun of(name: String): NameOfOptionalTextFacet {
            return NameOfOptionalTextFacet(name)
        }
    }
}
