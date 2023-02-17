package ch.cassiamon.engine.schema.types

import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName
import ch.cassiamon.pluginapi.registration.types.*

class IntegerNumberCalculatedFacet (
    conceptName: ConceptName,
    facetName: FacetName,
    facetDependencies: Set<FacetName>,
    val facetCalculationFunction: IntegerNumberFacetCalculationFunction,
) : CalculatedFacet(
    conceptName = conceptName,
    facetName = facetName,
    facetDependencies = facetDependencies,
) {
    override val facetType: FacetType
        get() = FacetType.INTEGER_NUMBER
}
