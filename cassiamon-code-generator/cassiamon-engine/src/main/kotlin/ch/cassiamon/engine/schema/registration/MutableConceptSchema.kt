package ch.cassiamon.engine.schema.registration

import ch.cassiamon.engine.schema.ConceptSchema
import ch.cassiamon.engine.schema.facets.FacetSchema
import ch.cassiamon.pluginapi.ConceptName


class MutableConceptSchema(override val conceptName: ConceptName,
                           override val parentConceptName: ConceptName?,
                           val mutableFacets: MutableList<FacetSchema<*>> = mutableListOf()
    ): ConceptSchema
{

    override val facets: List<FacetSchema<*>>
        get() = mutableFacets.toList()
}
