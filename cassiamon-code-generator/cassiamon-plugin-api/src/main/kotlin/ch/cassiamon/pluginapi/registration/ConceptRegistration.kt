package ch.cassiamon.pluginapi.registration

import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName
import ch.cassiamon.pluginapi.registration.types.*

interface ConceptRegistration {

    fun addTextFacet(
        facetName: FacetName,
        dependingOnFacets: Set<FacetName> = emptySet(),
        transformationFunction: TextFacetTransformationFunction? = null
    )

    fun addCalculatedTextFacet(
        facetName: FacetName,
        dependingOnFacets: Set<FacetName> = emptySet(),
        calculationFunction: TextFacetCalculationFunction,
    )

    fun addIntegerNumberFacet(
        facetName: FacetName,
        dependingOnFacets: Set<FacetName> = emptySet(),
        transformationFunction: IntegerNumberFacetTransformationFunction? = null,
    )

    fun addCalculatedIntegerNumberFacet(
        facetName: FacetName,
        dependingOnFacets: Set<FacetName> = emptySet(),
        calculationFunction: IntegerNumberFacetCalculationFunction,
    )

    fun addConceptReferenceFacet(
        facetName: FacetName,
        referencedConcept: ConceptName,
        dependingOnFacets: Set<FacetName> = emptySet(),
    )

    fun addCalculatedConceptReferenceFacet(
        facetName: FacetName,
        referencedConcept: ConceptName,
        dependingOnFacets: Set<FacetName> = emptySet(),
        calculationFunction: ConceptReferenceFacetCalculationFunction,
    )
}
