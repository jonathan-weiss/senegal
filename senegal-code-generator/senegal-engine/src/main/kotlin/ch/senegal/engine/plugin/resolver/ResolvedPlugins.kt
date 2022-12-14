package ch.senegal.engine.plugin.resolver

import ch.senegal.plugin.ConceptName
import ch.senegal.plugin.FacetName
import ch.senegal.plugin.PurposeName

/**
 * A hierarchical view to all plugins (= concept and purpose).
 */
class ResolvedPlugins(
    val allResolvedConcepts: Set<ResolvedConcept>,

    ) {
    val resolvedRootConcepts: List<ResolvedConcept>
        get() = allResolvedConcepts.filter { it.concept.enclosingConceptName == null }

    fun getConceptByConceptName(name: ConceptName): ResolvedConcept? {
        return allResolvedConcepts.associateBy { it.concept.conceptName }[name]
    }

    fun getResolvedFacet(purposeName: PurposeName, facetName: FacetName): ResolvedFacet? {
        return allResolvedConcepts.firstNotNullOfOrNull { it.getFacet(purposeName, facetName) }
    }

}
