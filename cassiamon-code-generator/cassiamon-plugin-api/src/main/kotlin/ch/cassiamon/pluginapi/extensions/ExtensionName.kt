package ch.cassiamon.pluginapi.extensions

import ch.cassiamon.pluginapi.rules.NameEnforcer


@JvmInline
value class ExtensionName private constructor(val name: String) {

    companion object {
        fun of(name: String): ExtensionName {
            NameEnforcer.isValidNameOrThrow(name)
            return ExtensionName(name)
        }
    }
}
