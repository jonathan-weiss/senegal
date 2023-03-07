package ch.cassiamon.engine.schema.registration

import ch.cassiamon.engine.schema.Concept
import ch.cassiamon.engine.schema.facets.FacetSchema
import ch.cassiamon.pluginapi.ConceptName


class MutableConcept(override val conceptName: ConceptName,
                     override val parentConceptName: ConceptName?,
                     val mutableFacets: MutableList<FacetSchema<*>> = mutableListOf()
    ): Concept
{

    override val facets: List<FacetSchema<*>>
        get() = mutableFacets.toList()
}
