package ch.senegal.plugin

import ch.senegal.plugin.rules.NameEnforcer

@JvmInline
value class PurposeFacetCombinedName private constructor (val name: String) {

    companion object {
        fun of(purposeName: PurposeName, facetName: FacetName): PurposeFacetCombinedName {
            return PurposeFacetCombinedName("${purposeName.name}${facetName.name}")
        }

        fun of(purposeAndFacetCombinedName: String): PurposeFacetCombinedName {
            NameEnforcer.isValidNameOrThrow(purposeAndFacetCombinedName)
            return PurposeFacetCombinedName(purposeAndFacetCombinedName)
        }

    }
}
