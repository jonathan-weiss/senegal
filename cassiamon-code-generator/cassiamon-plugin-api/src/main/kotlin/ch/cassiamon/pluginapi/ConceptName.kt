package ch.cassiamon.pluginapi

import ch.cassiamon.pluginapi.rules.NameEnforcer

@JvmInline
value class ConceptName private constructor(val name: String) {

    companion object {
        fun of(name: String): ch.cassiamon.pluginapi.ConceptName {
            ch.cassiamon.pluginapi.rules.NameEnforcer.isValidNameOrThrow(name)
            return ch.cassiamon.pluginapi.ConceptName(name)
        }
    }
}
