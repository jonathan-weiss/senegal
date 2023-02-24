package ch.cassiamon.pluginapi.registration

import ch.cassiamon.pluginapi.*
import ch.cassiamon.pluginapi.model.ConceptIdentifier
import ch.cassiamon.pluginapi.model.ConceptModelNode

interface ConceptRegistration {
    fun newChildConcept(conceptName: ConceptName, conceptRegistration: (ConceptRegistration.() -> Unit))

    fun addTextFacet(
        facetName: NameOfMandatoryTextFacet
    )

    fun addTextFacet(
        facetName: NameOfOptionalTextFacet
    )


    fun addCalculatedTextFacet(
        facetName: NameOfMandatoryTextFacet,
        calculationFunction: (conceptModelNode: ConceptModelNode) -> String,
    )

    fun addCalculatedTextFacet(
        facetName: NameOfOptionalTextFacet,
        calculationFunction: (conceptModelNode: ConceptModelNode) -> String?,
    )


    fun addIntegerNumberFacet(
        facetName: NameOfMandatoryIntegerNumberFacet
    )

    fun addIntegerNumberFacet(
        facetName: NameOfOptionalIntegerNumberFacet
    )

    fun addCalculatedIntegerNumberFacet(
        facetName: NameOfMandatoryIntegerNumberFacet,
        calculationFunction: (conceptModelNode: ConceptModelNode) -> Int,
    )
    fun addCalculatedIntegerNumberFacet(
        facetName: NameOfOptionalIntegerNumberFacet,
        calculationFunction: (conceptModelNode: ConceptModelNode) -> Int?,
    )

    fun addConceptReferenceFacet(
        facetName: NameOfMandatoryConceptReferenceFacet,
        referencedConcept: ConceptName,
    )

    fun addConceptReferenceFacet(
        facetName: NameOfOptionalConceptReferenceFacet,
        referencedConcept: ConceptName,
    )

    fun addCalculatedConceptReferenceFacet(
        facetName: NameOfMandatoryConceptReferenceFacet,
        referencedConcept: ConceptName,
        calculationFunction: (conceptModelNode: ConceptModelNode) -> ConceptIdentifier,
    )

    fun addCalculatedConceptReferenceFacet(
        facetName: NameOfOptionalConceptReferenceFacet,
        referencedConcept: ConceptName,
        calculationFunction: (conceptModelNode: ConceptModelNode) -> ConceptIdentifier?,
    )

}
