package ch.cassiamon.api.process.schema

import ch.cassiamon.api.NamedId

class ConceptName private constructor(name: String): NamedId(name) {

    companion object {
        fun of(name: String): ConceptName {
            return ConceptName(name)
        }
    }
}
