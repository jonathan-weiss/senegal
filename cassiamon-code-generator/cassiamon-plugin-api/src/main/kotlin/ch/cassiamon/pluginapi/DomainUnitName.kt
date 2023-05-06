package ch.cassiamon.pluginapi

import ch.cassiamon.pluginapi.rules.NameEnforcer

@JvmInline
value class DomainUnitName private constructor(val name: String) {

    companion object {
        fun of(name: String): DomainUnitName {
            NameEnforcer.isValidNameOrThrow(name)
            return DomainUnitName(name)
        }
    }
}
