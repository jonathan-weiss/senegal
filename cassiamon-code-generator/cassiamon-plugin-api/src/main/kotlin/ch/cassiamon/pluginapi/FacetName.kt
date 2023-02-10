package ch.cassiamon.pluginapi

import ch.cassiamon.pluginapi.rules.NameEnforcer

@JvmInline
value class FacetName private constructor(val name: String) {

    companion object {
        fun of(name: String): ch.cassiamon.pluginapi.FacetName {
            ch.cassiamon.pluginapi.rules.NameEnforcer.isValidNameOrThrow(name)
            return ch.cassiamon.pluginapi.FacetName(name)
        }
    }
}
