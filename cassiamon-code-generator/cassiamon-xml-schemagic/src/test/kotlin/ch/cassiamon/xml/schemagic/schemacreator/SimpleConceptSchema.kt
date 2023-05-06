package ch.cassiamon.xml.schemagic.schemacreator

import ch.cassiamon.api.ConceptName
import ch.cassiamon.api.model.facets.TemplateFacet
import ch.cassiamon.api.schema.ConceptSchema
import ch.cassiamon.api.schema.InputFacetSchema
import ch.cassiamon.api.schema.TemplateFacetSchema

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
