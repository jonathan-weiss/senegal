package ch.cassiamon.engine.model.validator

import ch.cassiamon.engine.inputsource.ModelConceptInputDataEntry
import ch.cassiamon.engine.model.types.ConceptReferenceFacetValue
import ch.cassiamon.engine.model.types.FacetValue
import ch.cassiamon.engine.model.types.IntegerNumberFacetValue
import ch.cassiamon.engine.model.types.TextFacetValue
import ch.cassiamon.engine.schema.Concept
import ch.cassiamon.engine.schema.Schema
import ch.cassiamon.engine.schema.facets.FacetType
import ch.cassiamon.engine.schema.facets.ManualFacetSchema
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
        entry.facetValueAccess
        entry.facetValueAccess.getFacetNames().forEach { facetName ->
            if(!schemaConcept.hasFacet(facetName)) {
                throw InvalidFacetConfigurationModelException(
                    conceptName = entry.conceptName,
                    conceptIdentifier = entry.conceptIdentifier,
                    facetName = facetName,
                    reason = "Facet with facet name '${facetName.name}' is not known by the schema. " +
                            "Known facets are: [${schemaConcept.facets.joinToString { it.facetDescriptor.facetName.name }}]"
                )
            }
            if(!schemaConcept.hasManualFacet(facetName)) {
                throw InvalidFacetConfigurationModelException(
                    conceptName = entry.conceptName,
                    conceptIdentifier = entry.conceptIdentifier,
                    facetName = facetName,
                    reason = "Facet with facet name '${facetName.name}' is known by the schema but " +
                            "a calculated facet. Data input is only allowed for manual facets." +
                            "Manual facets are: " +
                            "[${schemaConcept.facets.filter { it.facetDescriptor.isManualFacetValue }.joinToString { it.facetDescriptor.facetName.name }}]"
                )
            }
        }

        // iterate through all manual schema facets to validate
        schemaConcept.facets
            .filterIsInstance<ManualFacetSchema<*,*>>()
            .forEach { manualSchemaFacet ->
                val facetValue = null //entry.facetValuesMap[manualSchemaFacet.facetName]
                validateAgainstSchemaFacet(manualSchemaFacet, facetValue, entry)
            }
    }

    private fun validateAgainstSchemaFacet(schemaFacet: ManualFacetSchema<*,*>, facetValue: FacetValue?, entry: ModelConceptInputDataEntry) {
        if(facetValue == null) {
            throw InvalidFacetConfigurationModelException(
                conceptName = entry.conceptName,
                conceptIdentifier = entry.conceptIdentifier,
                facetName = schemaFacet.facetDescriptor.facetName,
                reason = "No data found for facet '${schemaFacet.facetDescriptor.facetName.name}'. "
            )
        }

        when(facetValue) {
            is TextFacetValue -> {
                if(schemaFacet.facetType != FacetType.TEXT) {
                    throw InvalidFacetConfigurationModelException(
                        conceptName = entry.conceptName,
                        conceptIdentifier = entry.conceptIdentifier,
                        facetName = schemaFacet.facetDescriptor.facetName,
                        reason = "Facet value is not of type '${FacetType.TEXT}'. "
                    )

                }
            }
            is IntegerNumberFacetValue -> {
                if(schemaFacet.facetType != FacetType.INTEGER_NUMBER) {
                    throw InvalidFacetConfigurationModelException(
                        conceptName = entry.conceptName,
                        conceptIdentifier = entry.conceptIdentifier,
                        facetName = schemaFacet.facetDescriptor.facetName,
                        reason = "Facet value is not of type '${FacetType.INTEGER_NUMBER}'. "
                    )

                }
            }
            is ConceptReferenceFacetValue -> {
                if(schemaFacet.facetType != FacetType.CONCEPT_REFERENCE) {
                    throw InvalidFacetConfigurationModelException(
                        conceptName = entry.conceptName,
                        conceptIdentifier = entry.conceptIdentifier,
                        facetName = schemaFacet.facetDescriptor.facetName,
                        reason = "Facet value is not of type '${FacetType.CONCEPT_REFERENCE}'. "
                    )
                }
            }
        }
    }


    private fun isValidParentConcept(schemaConcept: Concept, entry: ModelConceptInputDataEntry): Boolean {
        if(schemaConcept.parentConceptName != null && entry.parentConceptIdentifier == null) {
            return false
        }
        if(schemaConcept.parentConceptName == null && entry.parentConceptIdentifier != null) {
            return false
        }
        return true
    }

}
