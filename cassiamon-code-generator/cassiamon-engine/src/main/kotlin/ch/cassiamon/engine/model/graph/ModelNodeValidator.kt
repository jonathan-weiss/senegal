package ch.cassiamon.engine.model.graph

import ch.cassiamon.engine.model.inputsource.ModelConceptInputDataEntry
import ch.cassiamon.engine.model.types.ConceptReferenceFacetValue
import ch.cassiamon.engine.model.types.FacetValue
import ch.cassiamon.engine.model.types.IntegerNumberFacetValue
import ch.cassiamon.engine.model.types.TextFacetValue
import ch.cassiamon.engine.schema.types.*
import ch.cassiamon.pluginapi.model.exceptions.ConceptNotKnownModelException
import ch.cassiamon.pluginapi.model.exceptions.ConceptParentInvalidModelException
import ch.cassiamon.pluginapi.model.exceptions.InvalidFacetConfigurationModelException

object ModelNodeValidator {

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
        entry.facetValues.keys.forEach { entryFacetName ->
            if(!schemaConcept.hasFacet(entryFacetName)) {
                throw InvalidFacetConfigurationModelException(
                    conceptName = entry.conceptName,
                    conceptIdentifier = entry.conceptIdentifier,
                    facetName = entryFacetName,
                    reason = "Facet with facet name '${entryFacetName.name}' is not known by the schema. " +
                            "Known facets are: [${schemaConcept.facets.joinToString { it.facetName.name }}]"
                )
            }
            if(!schemaConcept.hasManualFacet(entryFacetName)) {
                throw InvalidFacetConfigurationModelException(
                    conceptName = entry.conceptName,
                    conceptIdentifier = entry.conceptIdentifier,
                    facetName = entryFacetName,
                    reason = "Facet with facet name '${entryFacetName.name}' is known by the schema but " +
                            "a calculated facet. Data input is only allowed for manual facets." +
                            "Manual facets are: " +
                            "[${schemaConcept.facets.filter { it.isManualFacet() }.joinToString { it.facetName.name }}]"
                )
            }
        }

        // iterate through all manual schema facets to validate
        schemaConcept.facets
            .filterIsInstance<ManualFacet>()
            .forEach { manualSchemaFacet ->
                val facetValue = entry.facetValues[manualSchemaFacet.facetName]
                validateAgainstSchemaFacet(manualSchemaFacet, facetValue, entry)
            }
    }

    private fun validateAgainstSchemaFacet(schemaFacet: ManualFacet, facetValue: FacetValue?, entry: ModelConceptInputDataEntry) {
        if(facetValue == null) {
            throw InvalidFacetConfigurationModelException(
                conceptName = entry.conceptName,
                conceptIdentifier = entry.conceptIdentifier,
                facetName = schemaFacet.facetName,
                reason = "No data found for facet '${schemaFacet.facetName.name}'. "
            )
        }

        when(facetValue) {
            is TextFacetValue -> {
                if(schemaFacet.facetType != FacetType.TEXT) {
                    throw InvalidFacetConfigurationModelException(
                        conceptName = entry.conceptName,
                        conceptIdentifier = entry.conceptIdentifier,
                        facetName = schemaFacet.facetName,
                        reason = "Facet value is not of type '${FacetType.TEXT}'. "
                    )

                }
            }
            is IntegerNumberFacetValue -> {
                if(schemaFacet.facetType != FacetType.INTEGER_NUMBER) {
                    throw InvalidFacetConfigurationModelException(
                        conceptName = entry.conceptName,
                        conceptIdentifier = entry.conceptIdentifier,
                        facetName = schemaFacet.facetName,
                        reason = "Facet value is not of type '${FacetType.INTEGER_NUMBER}'. "
                    )

                }
            }
            is ConceptReferenceFacetValue -> {
                if(schemaFacet.facetType != FacetType.CONCEPT_REFERENCE) {
                    throw InvalidFacetConfigurationModelException(
                        conceptName = entry.conceptName,
                        conceptIdentifier = entry.conceptIdentifier,
                        facetName = schemaFacet.facetName,
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
