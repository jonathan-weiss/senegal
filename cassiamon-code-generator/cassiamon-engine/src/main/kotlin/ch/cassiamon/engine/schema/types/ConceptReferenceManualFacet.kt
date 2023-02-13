package ch.cassiamon.engine.schema.types

import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName
import ch.cassiamon.pluginapi.registration.types.*

open class ConceptReferenceManualFacet(
    conceptName: ConceptName,
    facetName: FacetName,
    facetType: FacetType,
    facetDependencies: Set<FacetName>,
    facetTransformationFunction: ConceptReferenceFacetTransformationFunction,
    val referencedConceptName: ConceptName,
) : ManualFacet(
    conceptName = conceptName,
    facetName = facetName,
    facetType = facetType,
    facetDependencies = facetDependencies,
    facetTransformationFunction = facetTransformationFunction,
)
