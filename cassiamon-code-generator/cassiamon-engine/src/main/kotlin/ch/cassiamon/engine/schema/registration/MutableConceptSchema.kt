package ch.cassiamon.engine.schema.registration

import ch.cassiamon.engine.schema.ConceptSchema
import ch.cassiamon.engine.schema.facets.InputFacetSchema
import ch.cassiamon.engine.schema.facets.TemplateFacetSchema
import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.model.ConceptModelNodeCalculationData
import ch.cassiamon.pluginapi.model.facets.*


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

    private val templateFacetSchemaMap: Map<TemplateFacet<*>, TemplateFacetSchema<*>>
        get() = templateFacets.associateBy { templateFacetSchema -> templateFacetSchema.templateFacet }


    override fun <T> templateFacetSchemaOf(templateFacet: TemplateFacet<T>): TemplateFacetSchema<T> {
        val templateFacetSchema: TemplateFacetSchema<*> = templateFacetSchemaMap[templateFacet]
            ?: throw IllegalStateException("Could not find templateFacetSchema for template facet ${templateFacet.facetName}")

        @Suppress("UNCHECKED_CAST")
        return templateFacetSchema as TemplateFacetSchema<T>

    }
}
