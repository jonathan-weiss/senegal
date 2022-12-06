package ch.senegal.plugin

import ch.senegal.plugin.rules.NameEnforcer

@JvmInline
value class PurposeName(val name: String) {
    init {
        NameEnforcer.isValidNameOrThrow(name)
    }
}
