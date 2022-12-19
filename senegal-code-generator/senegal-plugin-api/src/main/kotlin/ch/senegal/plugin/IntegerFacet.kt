package ch.senegal.plugin

import ch.senegal.plugin.model.ModelNode

class IntegerFacet(
    facetName: FacetName,
    enclosingConceptName: ConceptName,
    isOnlyCalculated: Boolean,
    val enhanceFacetValue: (modelNode: ModelNode, facetValue: Int?) -> Int?
) : Facet(facetName, enclosingConceptName, isOnlyCalculated)
