package ch.cassiamon.pluginapi.registration

import ch.cassiamon.pluginapi.*
import ch.cassiamon.pluginapi.model.ConceptModelNode

interface ConceptRegistration {
    fun newChildConcept(conceptName: ConceptName, conceptRegistration: (ConceptRegistration.() -> Unit))

    fun addFacet(
        facetDescriptor: ManualMandatoryTextFacetDescriptor
    )

    fun addFacet(
        facetDescriptor: ManualOptionalTextFacetDescriptor
    )


    fun addFacet(
        facetDescriptor: CalculatedMandatoryTextFacetDescriptor,
        calculationFunction: (conceptModelNode: ConceptModelNode) -> String,
    )

    fun addFacet(
        facetDescriptor: CalculatedOptionalTextFacetDescriptor,
        calculationFunction: (conceptModelNode: ConceptModelNode) -> String?,
    )


    fun addFacet(
        facetDescriptor: ManualMandatoryIntegerNumberFacetDescriptor
    )

    fun addFacet(
        facetDescriptor: ManualOptionalIntegerNumberFacetDescriptor
    )

    fun addFacet(
        facetDescriptor: CalculatedMandatoryIntegerNumberFacetDescriptor,
        calculationFunction: (conceptModelNode: ConceptModelNode) -> Int,
    )
    fun addFacet(
        facetDescriptor: CalculatedOptionalIntegerNumberFacetDescriptor,
        calculationFunction: (conceptModelNode: ConceptModelNode) -> Int?,
    )

    fun addFacet(
        facetDescriptor: ManualMandatoryConceptReferenceFacetDescriptor,
    )

    fun addFacet(
        facetDescriptor: ManualOptionalConceptReferenceFacetDescriptor,
    )

    fun addFacet(
        facetDescriptor: CalculatedMandatoryConceptReferenceFacetDescriptor,
        calculationFunction: (conceptModelNode: ConceptModelNode) -> ConceptModelNode,
    )

    fun addFacet(
        facetDescriptor: CalculatedOptionalConceptReferenceFacetDescriptor,
        calculationFunction: (conceptModelNode: ConceptModelNode) -> ConceptModelNode?,
    )

}
