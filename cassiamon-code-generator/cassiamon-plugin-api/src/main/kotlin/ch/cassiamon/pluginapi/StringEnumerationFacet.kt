package ch.cassiamon.pluginapi

import ch.cassiamon.pluginapi.model.ModelNode

class StringEnumerationFacet(
    facetName: ch.cassiamon.pluginapi.FacetName,
    enclosingConceptName: ch.cassiamon.pluginapi.ConceptName,
    isOnlyCalculated: Boolean,
    val enumerationOptions: List<ch.cassiamon.pluginapi.StringEnumerationFacetOption>,
    val enhanceFacetValue: (modelNode: ch.cassiamon.pluginapi.model.ModelNode, facetValue: String?) -> String?
) : ch.cassiamon.pluginapi.Facet(facetName, enclosingConceptName, isOnlyCalculated)
