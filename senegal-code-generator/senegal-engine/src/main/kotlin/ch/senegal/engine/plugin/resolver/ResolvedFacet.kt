package ch.senegal.engine.plugin.resolver

import ch.senegal.plugin.Concept
import ch.senegal.plugin.Purpose
import ch.senegal.plugin.Facet
import ch.senegal.plugin.PurposeFacetCombinedName

class ResolvedFacet(
    val concept: Concept,
    val purpose: Purpose,
    val facet: Facet,
) {
    val purposeFacetName: PurposeFacetCombinedName
    get() = PurposeFacetCombinedName.of(purpose.purposeName, facet.facetName)
}
