package ch.cassiamon.engine.schema.types

import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName
import ch.cassiamon.pluginapi.registration.types.*

class ConceptReferenceCalculatedFacet constructor(
    conceptName: ConceptName,
    facetName: FacetName,
    facetType: FacetType,
    facetDependencies: Set<FacetName>,
    facetCalculationFunction: FacetCalculationFunction<*>,
    val referencedConceptName: ConceptName,
) : CalculatedFacet(
    conceptName = conceptName,
    facetName = facetName,
    facetType = facetType,
    facetDependencies = facetDependencies,
    facetCalculationFunction = facetCalculationFunction
)
