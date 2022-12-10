package ch.senegal.engine.plugin.resolver

import ch.senegal.plugin.Concept
import ch.senegal.plugin.Purpose
import ch.senegal.plugin.Facet

class ResolvedFacet(
    val concept: Concept,
    val purpose: Purpose,
    val facet: Facet,
) {
    val purposeFacetName: String
    get() = "${purpose.purposeName.name}${facet.facetName.name}"
}
