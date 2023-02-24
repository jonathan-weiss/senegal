package ch.cassiamon.engine.schema.registration

import ch.cassiamon.engine.schema.types.*
import ch.cassiamon.pluginapi.ConceptName


class MutableConcept(override val conceptName: ConceptName,
                     override val parentConceptName: ConceptName?,
                     val mutableFacets: MutableList<Facet> = mutableListOf()
    ): Concept
{

    override val facets: List<Facet>
        get() = mutableFacets.toList()
}
