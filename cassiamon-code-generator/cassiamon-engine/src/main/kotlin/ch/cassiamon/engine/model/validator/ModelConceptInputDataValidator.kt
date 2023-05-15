package ch.cassiamon.engine.model.validator

import ch.cassiamon.engine.inputsource.ModelConceptInputDataEntry
import ch.cassiamon.api.schema.ConceptSchema
import ch.cassiamon.engine.domain.Schema
import ch.cassiamon.api.schema.InputFacetSchema
import ch.cassiamon.api.model.exceptions.ConceptNotKnownModelException
import ch.cassiamon.api.model.exceptions.ConceptParentInvalidModelException
import ch.cassiamon.api.model.exceptions.InvalidFacetConfigurationModelException
import ch.cassiamon.api.model.facets.*

object ModelConceptInputDataValidator {

    internal fun validateSingleEntry(schema: Schema, entry: ModelConceptInputDataEntry) {
        if(!schema.hasConceptName(entry.conceptName)) {
            throw ConceptNotKnownModelException(entry.conceptName, entry.conceptIdentifier)
        }

        val schemaConcept = schema.conceptByConceptName(entry.conceptName)
        if(!isValidParentConcept(schemaConcept, entry)) {
            throw ConceptParentInvalidModelException(
                concept = entry.conceptName,
                conceptIdentifier = entry.conceptIdentifier,
                parentConceptIdentifier = entry.parentConceptIdentifier)
        }

        // iterate through all entry facet values to find obsolet ones
        entry.inputFacetValueAccess.getFacetNames().forEach { facetName ->
            if(!schemaConcept.hasInputFacet(facetName)) {
                throw InvalidFacetConfigurationModelException(
                    conceptName = entry.conceptName,
                    conceptIdentifier = entry.conceptIdentifier,
                    facetName = facetName,
                    reason = "Facet with facet name '${facetName.name}' is not known by the schema. " +
                            "Known facets are: [${schemaConcept.inputFacets.joinToString { it.inputFacet.facetName.name }}]"
                )
            }
        }

        // iterate through all schema facets to find missing ones/invalid ones
        schemaConcept.inputFacets.forEach { inputFacetSchema ->
            val facetValue = try {
                entry.inputFacetValueAccess.facetValue(inputFacetSchema.inputFacet)
            } catch (ex: IllegalStateException) {
                null
            }

            if(facetValue == null && inputFacetSchema.inputFacet.isMandatoryInputFacetValue) {
                throw InvalidFacetConfigurationModelException(
                    conceptName = entry.conceptName,
                    conceptIdentifier = entry.conceptIdentifier,
                    facetName = inputFacetSchema.inputFacet.facetName,
                    reason = "Mandatory facet with facet name '${inputFacetSchema.inputFacet.facetName.name}' is missing. "
                )
            }

            validateValueAgainstInputFacetSchema(inputFacetSchema, facetValue, entry)

        }
    }

    private fun validateValueAgainstInputFacetSchema(inputFacetSchema: InputFacetSchema<*>, facetValue: Any?, entry: ModelConceptInputDataEntry) {
        when(val inputFacet = inputFacetSchema.inputFacet) {
            is ConceptFacets.MandatoryConceptIdentifierInputAndConceptNodeTemplateFacet -> validateValueAgainstInputFacet(inputFacet, facetValue, entry)
            is NumberFacets.MandatoryNumberInputAndTemplateFacet -> validateValueAgainstInputFacet(inputFacet, facetValue, entry)
            is TextFacets.MandatoryTextInputAndTemplateFacet -> validateValueAgainstInputFacet(inputFacet, facetValue, entry)
            is ConceptFacets.OptionalConceptIdentifierInputAndConceptNodeTemplateFacet -> validateValueAgainstInputFacet(inputFacet, facetValue, entry)
            is NumberFacets.MandatoryNumberInputFacet -> validateValueAgainstInputFacet(inputFacet, facetValue, entry)
            is TextFacets.MandatoryTextInputFacet -> validateValueAgainstInputFacet(inputFacet, facetValue, entry)
            is NumberFacets.OptionalNumberInputFacet -> validateValueAgainstInputFacet(inputFacet, facetValue, entry)
        }
    }

    private inline fun <reified T> validateValueAgainstInputFacet(inputFacet: InputFacet<T>, facetValue: Any?, entry: ModelConceptInputDataEntry) {
        if(facetValue == null && inputFacet.isMandatoryInputFacetValue) {
            throw InvalidFacetConfigurationModelException(
                conceptName = entry.conceptName,
                conceptIdentifier = entry.conceptIdentifier,
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
                conceptName = entry.conceptName,
                conceptIdentifier = entry.conceptIdentifier,
                facetName = inputFacet.facetName,
                reason = "Facet value has wrong type for facet '${inputFacet.facetName.name}': " +
                        "Expected was '$expectedClass' but was '$actualClass'"
            )

        }

    }



    private fun isValidParentConcept(schemaConcept: ConceptSchema, entry: ModelConceptInputDataEntry): Boolean {
        if(schemaConcept.parentConceptName != null && entry.parentConceptIdentifier == null) {
            return false
        }
        if(schemaConcept.parentConceptName == null && entry.parentConceptIdentifier != null) {
            return false
        }
        return true
    }

}
