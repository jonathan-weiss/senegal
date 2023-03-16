package ch.cassiamon.engine.schema.registration

import ch.cassiamon.engine.schema.ConceptSchema
import ch.cassiamon.engine.schema.facets.InputFacetSchema
import ch.cassiamon.engine.schema.facets.TemplateFacetSchema
import ch.cassiamon.pluginapi.ConceptName


class MutableConceptSchema(override val conceptName: ConceptName,
                           override val parentConceptName: ConceptName?,
                           val mutableInputFacets: MutableList<InputFacetSchema<*>> = mutableListOf(),
                           val mutableTemplateFacets: MutableList<TemplateFacetSchema<*>> = mutableListOf(),
    ): ConceptSchema
{

    override val inputFacets: List<InputFacetSchema<*>>
        get() = mutableInputFacets.toList()
    override val templateFacets: List<TemplateFacetSchema<*>>
        get() = mutableTemplateFacets.toList()
}
