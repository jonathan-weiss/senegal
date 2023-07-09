package ch.cassiamon.engine.process.datacollection

import ch.cassiamon.api.process.datacollection.ConceptData
import ch.cassiamon.api.process.datacollection.exceptions.*
import ch.cassiamon.api.process.schema.ConceptSchema
import ch.cassiamon.api.process.schema.SchemaAccess

object ConceptDataValidator {

    fun validateSingleEntry(schema: SchemaAccess, conceptData: ConceptData) {
        validateConceptBase(schema, conceptData)

        val schemaConcept = schema.conceptByConceptName(conceptData.conceptName)
        validateParentConceptBase(schemaConcept, conceptData)

        validateForObsoletFacets(schemaConcept, conceptData)
        validateForMissingMandatoryFacets(schemaConcept, conceptData)

        validateForFacetType(schemaConcept, conceptData)
    }

    private fun validateConceptBase(schema: SchemaAccess, conceptData: ConceptData) {
        if(!schema.hasConceptName(conceptData.conceptName)) {
            throw UnknownConceptException(conceptData.conceptName, conceptData.conceptIdentifier)
        }
    }

    private fun validateParentConceptBase(schemaConcept: ConceptSchema, conceptData: ConceptData) {
        if(!isValidParentConcept(schemaConcept, conceptData)) {
            throw InvalidConceptParentException(
                concept = conceptData.conceptName,
                conceptIdentifier = conceptData.conceptIdentifier,
                parentConceptIdentifier = conceptData.parentConceptIdentifier)
        }
    }

    private fun validateForObsoletFacets(schemaConcept: ConceptSchema, conceptData: ConceptData) {
        // iterate through all entry facet values to find obsolet ones
        conceptData.getFacetNames().forEach { facetName ->
            if(!schemaConcept.hasFacet(facetName)) {
                throw UnknownFacetNameException(
                    concept = conceptData.conceptName,
                    conceptIdentifier = conceptData.conceptIdentifier,
                    facetName = facetName,
                    reason = "Facet with facet name '${facetName.name}' is not known by the schema. " +
                            "Known facets are: [${schemaConcept.facetNames.joinToString { it.name }}]"
                )
            }
        }
    }

    private fun validateForMissingMandatoryFacets(schemaConcept: ConceptSchema, conceptData: ConceptData) {
        // iterate through all schema facets to find missing ones
        schemaConcept.facets
            .filter { facetSchema -> facetSchema.mandatory }
            .forEach { facetSchema ->
                if(!conceptData.hasFacet(facetSchema.facetName)) {
                    throw MissingFacetValueException(
                        concept = conceptData.conceptName,
                        conceptIdentifier = conceptData.conceptIdentifier,
                        facetName = facetSchema.facetName,
                    )
                }

                val facetValue = conceptData.getFacet(facetSchema.facetName)

                if(facetValue == null && facetSchema.mandatory) {
                    throw MissingFacetValueException(
                        concept = conceptData.conceptName,
                        conceptIdentifier = conceptData.conceptIdentifier,
                        facetName = facetSchema.facetName,
                    )
                }
        }
    }

    private fun validateForFacetType(schemaConcept: ConceptSchema, conceptData: ConceptData) {
        schemaConcept.facets.forEach { facetSchema ->
            if(conceptData.hasFacet(facetSchema.facetName)) {
                val facetValue = conceptData.getFacet(facetSchema.facetName) ?: return@forEach

                if(!facetSchema.facetType.isCompatibleType(facetValue)) {
                    val actualClass = facetValue::class

                    throw WrongTypeForFacetValueException(
                        concept = conceptData.conceptName,
                        conceptIdentifier = conceptData.conceptIdentifier,
                        facetName = facetSchema.facetName,
                        reason = "A facet of type '${facetSchema.facetType}' can not have a value of type '$actualClass'"
                    )
                }
            }
        }

    }

    private fun isValidParentConcept(schemaConcept: ConceptSchema, conceptData: ConceptData): Boolean {
        if(schemaConcept.parentConceptName != null && conceptData.parentConceptIdentifier == null) {
            return false
        }

        return !(schemaConcept.parentConceptName == null && conceptData.parentConceptIdentifier != null)
    }

}
