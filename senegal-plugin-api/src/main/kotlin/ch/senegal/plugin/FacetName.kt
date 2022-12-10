package ch.senegal.plugin

import ch.senegal.plugin.rules.NameEnforcer

@JvmInline
value class FacetName private constructor(val name: String) {

    companion object {
        fun of(name: String): FacetName {
            NameEnforcer.isValidNameOrThrow(name)
            return FacetName(name)
        }
    }
}
