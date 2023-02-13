package ch.cassiamon.engine.model.types

import ch.cassiamon.pluginapi.rules.NameEnforcer


@JvmInline
value class ConceptIdentifier private constructor(val code: String) {

    companion object {
        fun of(name: String): ConceptIdentifier {
            NameEnforcer.isValidNameOrThrow(name)
            return ConceptIdentifier(name)
        }
    }
}
