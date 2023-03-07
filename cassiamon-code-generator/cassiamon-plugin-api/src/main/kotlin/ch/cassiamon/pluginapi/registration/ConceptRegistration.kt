package ch.cassiamon.pluginapi.registration

import ch.cassiamon.pluginapi.*
import ch.cassiamon.pluginapi.model.ConceptIdentifier
import ch.cassiamon.pluginapi.model.ConceptModelNode

interface ConceptRegistration {
    fun newChildConcept(conceptName: ConceptName, conceptRegistration: (ConceptRegistration.() -> Unit))

    fun addTextFacet(
        facetDescriptor: ManualMandatoryTextFacetDescriptor
    )

    fun addTextFacet(
        facetDescriptor: ManualOptionalTextFacetDescriptor
    )


    fun addCalculatedTextFacet(
        facetDescriptor: CalculatedMandatoryTextFacetDescriptor,
        calculationFunction: (conceptModelNode: ConceptModelNode) -> String,
    )

    fun addCalculatedTextFacet(
        facetDescriptor: CalculatedOptionalTextFacetDescriptor,
        calculationFunction: (conceptModelNode: ConceptModelNode) -> String?,
    )


    fun addIntegerNumberFacet(
        facetDescriptor: ManualMandatoryIntegerNumberFacetDescriptor
    )

    fun addIntegerNumberFacet(
        facetDescriptor: ManualOptionalIntegerNumberFacetDescriptor
    )

    fun addCalculatedIntegerNumberFacet(
        facetDescriptor: CalculatedMandatoryIntegerNumberFacetDescriptor,
        calculationFunction: (conceptModelNode: ConceptModelNode) -> Int,
    )
    fun addCalculatedIntegerNumberFacet(
        facetDescriptor: CalculatedOptionalIntegerNumberFacetDescriptor,
        calculationFunction: (conceptModelNode: ConceptModelNode) -> Int?,
    )

    fun addConceptReferenceFacet(
        facetDescriptor: ManualMandatoryConceptReferenceFacetDescriptor,
    )

    fun addConceptReferenceFacet(
        facetDescriptor: ManualOptionalConceptReferenceFacetDescriptor,
    )

    fun addCalculatedConceptReferenceFacet(
        facetDescriptor: CalculatedMandatoryConceptReferenceFacetDescriptor,
        calculationFunction: (conceptModelNode: ConceptModelNode) -> ConceptModelNode,
    )

    fun addCalculatedConceptReferenceFacet(
        facetDescriptor: CalculatedOptionalConceptReferenceFacetDescriptor,
        calculationFunction: (conceptModelNode: ConceptModelNode) -> ConceptModelNode?,
    )

}
