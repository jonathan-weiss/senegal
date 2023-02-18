package ch.cassiamon.engine.schema.types

import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName
import ch.cassiamon.pluginapi.model.ConceptIdentifier
import ch.cassiamon.pluginapi.registration.types.*

class ConceptReferenceCalculatedFacet(
    conceptName: ConceptName,
    facetName: FacetName,
    facetDependencies: Set<FacetName>,
    val facetCalculationFunction: ConceptReferenceFacetCalculationFunction,
    val referencedConceptName: ConceptName,
) : CalculatedFacet(
    conceptName = conceptName,
    facetName = facetName,
    facetDependencies = facetDependencies,
) {
    override val facetType: FacetType
        get() = FacetType.CONCEPT_REFERENCE
}

