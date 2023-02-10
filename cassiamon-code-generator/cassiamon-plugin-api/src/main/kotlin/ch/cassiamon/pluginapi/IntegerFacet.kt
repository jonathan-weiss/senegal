package ch.cassiamon.pluginapi

import ch.cassiamon.pluginapi.model.ModelNode

class IntegerFacet(
    facetName: ch.cassiamon.pluginapi.FacetName,
    enclosingConceptName: ch.cassiamon.pluginapi.ConceptName,
    isOnlyCalculated: Boolean,
    val enhanceFacetValue: (modelNode: ch.cassiamon.pluginapi.model.ModelNode, facetValue: Int?) -> Int?
) : ch.cassiamon.pluginapi.Facet(facetName, enclosingConceptName, isOnlyCalculated)
