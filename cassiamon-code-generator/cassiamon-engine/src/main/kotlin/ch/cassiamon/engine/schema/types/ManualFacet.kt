package ch.cassiamon.engine.schema.types

import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName
import ch.cassiamon.pluginapi.registration.types.*

open class ManualFacet constructor(
    conceptName: ConceptName,
    facetName: FacetName,
    facetType: FacetType,
    facetDependencies: Set<FacetName>,
    val facetTransformationFunction: FacetTransformationFunction<*>,
) : Facet(
    conceptName = conceptName,
    facetName = facetName,
    facetType = facetType,
    facetDependencies = facetDependencies
)
