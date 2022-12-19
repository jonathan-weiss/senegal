package ch.senegal.plugin

import ch.senegal.plugin.model.ModelNode

class StringEnumerationFacet(
    facetName: FacetName,
    enclosingConceptName: ConceptName,
    isOnlyCalculated: Boolean,
    val enumerationOptions: List<StringEnumerationFacetOption>,
    val enhanceFacetValue: (modelNode: ModelNode, facetValue: String?) -> String?
) : Facet(facetName, enclosingConceptName, isOnlyCalculated)
