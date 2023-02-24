package ch.cassiamon.pluginapi

import ch.cassiamon.pluginapi.rules.NameEnforcer


sealed class FacetName constructor(val name: String) {

    init {
        NameEnforcer.isValidNameOrThrow(name)
    }
}
