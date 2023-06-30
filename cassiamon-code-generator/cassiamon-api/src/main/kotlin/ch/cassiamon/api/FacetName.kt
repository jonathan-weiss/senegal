package ch.cassiamon.api

class FacetName private constructor(name: String): NamedId(name) {

    companion object {
        fun of(name: String): FacetName {
            return FacetName(name)
        }
    }
}
