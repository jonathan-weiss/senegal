package ch.cassiamon.pluginapi

import ch.cassiamon.pluginapi.rules.NameEnforcer

@JvmInline
value class PurposeName private constructor(val name: String) {

    companion object {
        fun of(name: String): ch.cassiamon.pluginapi.PurposeName {
            ch.cassiamon.pluginapi.rules.NameEnforcer.isValidNameOrThrow(name)
            return ch.cassiamon.pluginapi.PurposeName(name)
        }
    }
}
