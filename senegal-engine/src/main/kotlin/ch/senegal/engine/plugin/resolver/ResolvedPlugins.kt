package ch.senegal.engine.plugin.resolver

import ch.senegal.plugin.ConceptName
import ch.senegal.plugin.DecorName
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

    fun getResolvedDecor(purposeName: PurposeName, decorName: DecorName): ResolvedPurposeDecor? {
        val combinedName = "${purposeName.name}${decorName.name}"
        return allResolvedConcepts.firstNotNullOfOrNull { it.getPurposeDecorByCombinedName(combinedName) }
    }

}
