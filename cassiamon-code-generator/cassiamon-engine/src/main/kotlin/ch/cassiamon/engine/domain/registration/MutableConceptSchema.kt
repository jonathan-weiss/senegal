package ch.cassiamon.engine.domain.registration

import ch.cassiamon.api.schema.ConceptSchema
import ch.cassiamon.api.schema.InputFacetSchema
import ch.cassiamon.api.schema.TemplateFacetSchema
import ch.cassiamon.api.ConceptName
import ch.cassiamon.api.model.facets.*


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
