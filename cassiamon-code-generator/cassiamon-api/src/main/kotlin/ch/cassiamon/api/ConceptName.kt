package ch.cassiamon.api

import ch.cassiamon.api.rules.NameEnforcer

@JvmInline
value class ConceptName private constructor(val name: String) {

    companion object {
        fun of(name: String): ConceptName {
            NameEnforcer.isValidNameOrThrow(name)
            return ConceptName(name)
        }
    }
}
