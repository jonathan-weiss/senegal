package ch.senegal.plugin

import ch.senegal.plugin.model.ModelNode

class StringFacet(
    facetName: FacetName,
    enclosingConceptName: ConceptName,
    isOnlyCalculated: Boolean,
    val enhanceFacetValue: (modelNode: ModelNode, facetValue: String?) -> String?
) : Facet(facetName, enclosingConceptName, isOnlyCalculated)
