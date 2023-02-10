package ch.cassiamon.pluginapi

import ch.cassiamon.pluginapi.rules.NameEnforcer

@JvmInline
value class PurposeFacetCombinedName private constructor (val name: String) {

    companion object {
        fun of(purposeName: ch.cassiamon.pluginapi.PurposeName, facetName: ch.cassiamon.pluginapi.FacetName): ch.cassiamon.pluginapi.PurposeFacetCombinedName {
            return ch.cassiamon.pluginapi.PurposeFacetCombinedName.Companion.of("${purposeName.name}${facetName.name}")
        }

        fun of(purposeAndFacetCombinedName: String): ch.cassiamon.pluginapi.PurposeFacetCombinedName {
            ch.cassiamon.pluginapi.rules.NameEnforcer.isValidNameOrThrow(purposeAndFacetCombinedName)
            return ch.cassiamon.pluginapi.PurposeFacetCombinedName(purposeAndFacetCombinedName)
        }

    }
}
