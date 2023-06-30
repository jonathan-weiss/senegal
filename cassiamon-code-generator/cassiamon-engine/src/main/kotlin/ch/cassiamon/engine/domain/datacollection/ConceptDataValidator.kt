package ch.cassiamon.engine.domain.datacollection

import ch.cassiamon.api.model.exceptions.ConceptNotKnownModelException
import ch.cassiamon.api.model.exceptions.ConceptParentInvalidModelException
import ch.cassiamon.api.model.exceptions.InvalidFacetConfigurationModelException
import ch.cassiamon.api.model.facets.ConceptFacets
import ch.cassiamon.api.model.facets.InputFacet
import ch.cassiamon.api.model.facets.NumberFacets
import ch.cassiamon.api.model.facets.TextFacets
import ch.cassiamon.api.schema.ConceptSchema
import ch.cassiamon.api.schema.InputFacetSchema
import ch.cassiamon.api.schema.SchemaAccess

object ConceptDataValidator {

    internal fun validateSingleEntry(schema: SchemaAccess, conceptData: ConceptData) {
        if(!schema.hasConceptName(conceptData.conceptName)) {
            throw ConceptNotKnownModelException(conceptData.conceptName, conceptData.conceptIdentifier)
        }

        val schemaConcept = schema.conceptByConceptName(conceptData.conceptName)
        if(!isValidParentConcept(schemaConcept, conceptData)) {
            throw ConceptParentInvalidModelException(
                concept = conceptData.conceptName,
                conceptIdentifier = conceptData.conceptIdentifier,
                parentConceptIdentifier = conceptData.parentConceptIdentifier)
        }

        // iterate through all entry facet values to find obsolet ones
        conceptData.facets.keys.forEach { facetName ->
            if(!schemaConcept.hasInputFacet(facetName)) {
                throw InvalidFacetConfigurationModelException(
                    conceptName = conceptData.conceptName,
                    conceptIdentifier = conceptData.conceptIdentifier,
                    facetName = facetName,
                    reason = "Facet with facet name '${facetName.name}' is not known by the schema. " +
                            "Known facets are: [${schemaConcept.inputFacets.joinToString { it.inputFacet.facetName.name }}]"
                )
            }
        }

        // iterate through all schema facets to find missing ones/invalid ones
        schemaConcept.inputFacets.forEach { inputFacetSchema ->
            val facetValue = conceptData.facets[inputFacetSchema.inputFacet.facetName]

            // TODO This seems to be a duplication in method #validateValueAgainstInputFacet
            if(facetValue == null && inputFacetSchema.inputFacet.isMandatoryInputFacetValue) {
                throw InvalidFacetConfigurationModelException(
                    conceptName = conceptData.conceptName,
                    conceptIdentifier = conceptData.conceptIdentifier,
                    facetName = inputFacetSchema.inputFacet.facetName,
                    reason = "Mandatory facet with facet name '${inputFacetSchema.inputFacet.facetName.name}' is missing. "
                )
            }

            validateValueAgainstInputFacetSchema(inputFacetSchema, facetValue, conceptData)

        }
    }

    private fun validateValueAgainstInputFacetSchema(inputFacetSchema: InputFacetSchema<*>, facetValue: Any?, conceptData: ConceptData) {
        when(val inputFacet = inputFacetSchema.inputFacet) {
            is ConceptFacets.MandatoryConceptIdentifierInputAndConceptNodeTemplateFacet -> validateValueAgainstInputFacet(inputFacet, facetValue, conceptData)
            is NumberFacets.MandatoryNumberInputAndTemplateFacet -> validateValueAgainstInputFacet(inputFacet, facetValue, conceptData)
            is TextFacets.MandatoryTextInputAndTemplateFacet -> validateValueAgainstInputFacet(inputFacet, facetValue, conceptData)
            is ConceptFacets.OptionalConceptIdentifierInputAndConceptNodeTemplateFacet -> validateValueAgainstInputFacet(inputFacet, facetValue, conceptData)
            is NumberFacets.MandatoryNumberInputFacet -> validateValueAgainstInputFacet(inputFacet, facetValue, conceptData)
            is TextFacets.MandatoryTextInputFacet -> validateValueAgainstInputFacet(inputFacet, facetValue, conceptData)
            is NumberFacets.OptionalNumberInputFacet -> validateValueAgainstInputFacet(inputFacet, facetValue, conceptData)
        }
    }

    private inline fun <reified T> validateValueAgainstInputFacet(inputFacet: InputFacet<T>, facetValue: Any?, conceptData: ConceptData) {
        if(facetValue == null && inputFacet.isMandatoryInputFacetValue) {
            throw InvalidFacetConfigurationModelException(
                conceptName = conceptData.conceptName,
                conceptIdentifier = conceptData.conceptIdentifier,
                facetName = inputFacet.facetName,
                reason = "No data found for mandatory facet '${inputFacet.facetName.name}'. "
            )
        }

        if(facetValue == null) {
            return
        }

        if(facetValue !is T) {
            val expectedClass = T::class
            val actualClass = facetValue::class

            throw InvalidFacetConfigurationModelException(
                conceptName = conceptData.conceptName,
                conceptIdentifier = conceptData.conceptIdentifier,
                facetName = inputFacet.facetName,
                reason = "Facet value has wrong type for facet '${inputFacet.facetName.name}': " +
                        "Expected was '$expectedClass' but was '$actualClass'"
            )

        }

    }



    private fun isValidParentConcept(schemaConcept: ConceptSchema, conceptData: ConceptData): Boolean {
        if(schemaConcept.parentConceptName != null && conceptData.parentConceptIdentifier == null) {
            return false
        }

        return !(schemaConcept.parentConceptName == null && conceptData.parentConceptIdentifier != null)
    }

}
