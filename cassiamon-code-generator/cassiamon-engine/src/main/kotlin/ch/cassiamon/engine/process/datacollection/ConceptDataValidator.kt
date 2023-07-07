package ch.cassiamon.engine.process.datacollection

import ch.cassiamon.api.model.exceptions.ConceptNotKnownModelException
import ch.cassiamon.api.model.exceptions.ConceptParentInvalidModelException
import ch.cassiamon.api.model.exceptions.InvalidFacetConfigurationModelException
import ch.cassiamon.api.process.datacollection.ConceptData
import ch.cassiamon.api.process.schema.ConceptSchema
import ch.cassiamon.api.process.schema.FacetSchema
import ch.cassiamon.api.process.schema.SchemaAccess

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
            if(!schemaConcept.hasFacet(facetName)) {
                throw InvalidFacetConfigurationModelException(
                    conceptName = conceptData.conceptName,
                    conceptIdentifier = conceptData.conceptIdentifier,
                    facetName = facetName,
                    reason = "Facet with facet name '${facetName.name}' is not known by the schema. " +
                            "Known facets are: [${schemaConcept.facetNames.joinToString { it.name }}]"
                )
            }
        }

        // iterate through all schema facets to find missing ones/invalid ones
        schemaConcept.facets.forEach { facetSchema ->
            val facetValue = conceptData.facets[facetSchema.facetName]

            // TODO This seems to be a duplication in method #validateValueAgainstInputFacet
            if(facetValue == null && facetSchema.mandatory) {
                throw InvalidFacetConfigurationModelException(
                    conceptName = conceptData.conceptName,
                    conceptIdentifier = conceptData.conceptIdentifier,
                    facetName = facetSchema.facetName,
                    reason = "Mandatory facet with facet name '${facetSchema.facetName.name}' is missing. "
                )
            }

            validateValueAgainstInputFacetSchema(facetSchema, facetValue, conceptData)

        }
    }

    private fun validateValueAgainstInputFacetSchema(facetSchema: FacetSchema, facetValue: Any?, conceptData: ConceptData) {
        if(facetValue == null && facetSchema.mandatory) {
            throw InvalidFacetConfigurationModelException(
                conceptName = conceptData.conceptName,
                conceptIdentifier = conceptData.conceptIdentifier,
                facetName = facetSchema.facetName,
                reason = "No data found for mandatory facet '${facetSchema.facetName.name}'. "
            )
        }

        if(facetValue == null) {
            return
        }

        if(!facetSchema.facetType.isCompatibleType(facetValue)) {
            val expectedClass = facetSchema.facetType.typeClass
            val actualClass = facetValue::class

            throw InvalidFacetConfigurationModelException(
                conceptName = conceptData.conceptName,
                conceptIdentifier = conceptData.conceptIdentifier,
                facetName = facetSchema.facetName,
                reason = "Facet value has wrong type for facet '${facetSchema.facetName.name}': " +
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
