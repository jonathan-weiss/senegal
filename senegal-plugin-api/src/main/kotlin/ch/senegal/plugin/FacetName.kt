package ch.senegal.plugin

import ch.senegal.plugin.rules.NameEnforcer

@JvmInline
value class FacetName(val name: String) {
    init {
        NameEnforcer.isValidNameOrThrow(name)
    }

}
