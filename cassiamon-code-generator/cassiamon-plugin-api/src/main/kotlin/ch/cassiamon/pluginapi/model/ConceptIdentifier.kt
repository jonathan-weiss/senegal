package ch.cassiamon.pluginapi.model

import ch.cassiamon.pluginapi.rules.NameEnforcer


@JvmInline
value class ConceptIdentifier private constructor(val code: String) {

    companion object {
        fun of(name: String): ConceptIdentifier {
            NameEnforcer.isValidIdentifierOrThrow(name)
            return ConceptIdentifier(name)
        }
    }
}
