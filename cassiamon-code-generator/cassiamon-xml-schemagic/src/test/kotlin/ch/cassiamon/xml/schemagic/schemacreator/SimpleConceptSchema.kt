package ch.cassiamon.xml.schemagic.schemacreator

import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.model.facets.TemplateFacet
import ch.cassiamon.pluginapi.schema.ConceptSchema
import ch.cassiamon.pluginapi.schema.InputFacetSchema
import ch.cassiamon.pluginapi.schema.TemplateFacetSchema

class SimpleConceptSchema(
    override val conceptName: ConceptName,
    override val parentConceptName: ConceptName?,
    override val inputFacets: List<InputFacetSchema<*>>,
    override val templateFacets: List<TemplateFacetSchema<*>> = emptyList()
) : ConceptSchema {
    override fun <T> templateFacetSchemaOf(templateFacet: TemplateFacet<T>): TemplateFacetSchema<T> {
        throw IllegalStateException("Template facets are not supported.")
    }
}
