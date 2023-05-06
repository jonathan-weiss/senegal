package ch.cassiamon.api.extensions

import ch.cassiamon.api.rules.NameEnforcer


@JvmInline
value class ExtensionName private constructor(val name: String) {

    companion object {
        fun of(name: String): ExtensionName {
            NameEnforcer.isValidNameOrThrow(name)
            return ExtensionName(name)
        }
    }
}
