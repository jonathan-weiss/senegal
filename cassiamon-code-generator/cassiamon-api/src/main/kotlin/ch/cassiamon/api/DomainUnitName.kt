package ch.cassiamon.api

import ch.cassiamon.api.rules.NameEnforcer

@JvmInline
value class DomainUnitName private constructor(val name: String) {

    companion object {
        fun of(name: String): DomainUnitName {
            NameEnforcer.isValidNameOrThrow(name)
            return DomainUnitName(name)
        }
    }
}
