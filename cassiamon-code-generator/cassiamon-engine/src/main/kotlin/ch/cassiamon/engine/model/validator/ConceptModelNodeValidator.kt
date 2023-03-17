package ch.cassiamon.engine.model.validator

import ch.cassiamon.engine.inputsource.ModelConceptInputDataEntry
import ch.cassiamon.engine.schema.ConceptSchema
import ch.cassiamon.engine.schema.Schema
import ch.cassiamon.engine.schema.facets.InputFacetSchema
import ch.cassiamon.pluginapi.model.exceptions.ConceptNotKnownModelException
import ch.cassiamon.pluginapi.model.exceptions.ConceptParentInvalidModelException
import ch.cassiamon.pluginapi.model.exceptions.InvalidFacetConfigurationModelException

object ConceptModelNodeValidator {

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

        // iterate through all entry facet values to find invalid/obsolet ones
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

        // iterate through all manual schema facets to validate
//        schemaConcept.inputFacets
//            .forEach { inputFacetSchema ->
//                val facetValue = entry.inputFacetValueAccess.facetValue(inputFacetSchema)
//                validateValueAgainstInputSchemaFacet(inputFacetSchema, facetValue, entry)
//            }
    }

    private fun validateValueAgainstInputSchemaFacet(schemaFacet: InputFacetSchema<*>, facetValue: Any?, entry: ModelConceptInputDataEntry) {
        // TODO check if this is an optional input facet schema
        if(facetValue == null) {
            throw InvalidFacetConfigurationModelException(
                conceptName = entry.conceptName,
                conceptIdentifier = entry.conceptIdentifier,
                facetName = schemaFacet.inputFacet.facetName,
                reason = "No data found for facet '${schemaFacet.inputFacet.facetName.name}'. "
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
