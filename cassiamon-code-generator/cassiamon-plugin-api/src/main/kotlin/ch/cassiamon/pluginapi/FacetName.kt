package ch.cassiamon.pluginapi

import ch.cassiamon.pluginapi.rules.NameEnforcer


sealed class FacetName constructor(val name: String, val isMandatoryFacetValue: Boolean) {

    init {
        NameEnforcer.isValidNameOrThrow(name)
    }

    val isOptionalFacetValue: Boolean
        get() = !isMandatoryFacetValue

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FacetName

        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }


}
