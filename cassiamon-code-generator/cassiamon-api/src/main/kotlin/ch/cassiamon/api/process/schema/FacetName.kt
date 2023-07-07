package ch.cassiamon.api.process.schema

import ch.cassiamon.api.NamedId

class FacetName private constructor(name: String): NamedId(name) {

    companion object {
        fun of(name: String): FacetName {
            return FacetName(name)
        }
    }
}
