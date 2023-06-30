package ch.cassiamon.xml.schemagic.schemacreator

import ch.cassiamon.api.ConceptName
import ch.cassiamon.api.FacetName
import ch.cassiamon.api.model.facets.TemplateFacet
import ch.cassiamon.api.schema.*
import ch.cassiamon.engine.domain.registration.FacetSchemaImpl

class SimpleConceptSchema(
    override val conceptName: ConceptName,
    override val parentConceptName: ConceptName?,
    override val inputFacets: List<InputFacetSchema<*>>,
    override val templateFacets: List<TemplateFacetSchema<*>> = emptyList()
) : ConceptSchema {
    override fun <T> templateFacetSchemaOf(templateFacet: TemplateFacet<T>): TemplateFacetSchema<T> {
        throw IllegalStateException("Template facets are not supported.")
    }

    override val facetNames: List<FacetName>
        get() = inputFacets.map { it.inputFacet.facetName }

    override val facets: List<FacetSchema>
        get() = inputFacets.map {
            FacetSchemaImpl(
                facetName = it.inputFacet.facetName,
                facetType = FacetTypeEnum.TEXT,
                mandatory = it.inputFacet.isMandatoryInputFacetValue
            )
        }


}
