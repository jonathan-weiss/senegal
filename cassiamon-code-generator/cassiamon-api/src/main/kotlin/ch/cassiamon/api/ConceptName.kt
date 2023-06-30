package ch.cassiamon.api

class ConceptName private constructor(name: String): NamedId(name) {

    companion object {
        fun of(name: String): ConceptName {
            return ConceptName(name)
        }
    }
}
