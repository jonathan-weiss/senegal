package ch.cassiamon.api.extensions

import ch.cassiamon.api.NamedId


class ExtensionName private constructor(name: String): NamedId(name) {

    companion object {
        fun of(name: String): ExtensionName {
            return ExtensionName(name)
        }
    }
}
