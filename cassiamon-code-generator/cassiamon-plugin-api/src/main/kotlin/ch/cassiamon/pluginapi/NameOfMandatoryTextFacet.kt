package ch.cassiamon.pluginapi


class NameOfMandatoryTextFacet private constructor(name: String): FacetName(name) {

    companion object {
        fun of(name: String): NameOfMandatoryTextFacet {
            return NameOfMandatoryTextFacet(name)
        }
    }
}
