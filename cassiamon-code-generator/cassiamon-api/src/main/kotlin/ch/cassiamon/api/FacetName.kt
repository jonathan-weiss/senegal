package ch.cassiamon.api

import ch.cassiamon.api.rules.NameEnforcer

@JvmInline
value class FacetName private constructor(val name: String) {

    companion object {
        fun of(name: String): FacetName {
            NameEnforcer.isValidNameOrThrow(name)
            return FacetName(name)
        }
    }
}
