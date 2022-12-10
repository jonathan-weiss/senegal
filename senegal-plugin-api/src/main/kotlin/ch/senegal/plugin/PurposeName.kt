package ch.senegal.plugin

import ch.senegal.plugin.rules.NameEnforcer

@JvmInline
value class PurposeName private constructor(val name: String) {

    companion object {
        fun of(name: String): PurposeName {
            NameEnforcer.isValidNameOrThrow(name)
            return PurposeName(name)
        }
    }
}
