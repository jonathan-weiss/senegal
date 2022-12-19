package ch.senegal.plugin

import ch.senegal.plugin.model.ModelNode

class BooleanFacet(
    facetName: FacetName,
    enclosingConceptName: ConceptName,
    isOnlyCalculated: Boolean,
    val enhanceFacetValue: (modelNode: ModelNode, facetValue: Boolean?) -> Boolean?
) : Facet(facetName, enclosingConceptName, isOnlyCalculated)
