package ch.cassiamon.pluginapi

class StringEnumerationFacetSchema(
    facetName: ch.cassiamon.pluginapi.FacetName,
    enclosingConceptName: ch.cassiamon.pluginapi.ConceptName,
    isOnlyCalculated: Boolean,
    val enumerationOptions: List<ch.cassiamon.pluginapi.StringEnumerationFacetOption>,
    val enhanceFacetValue: (modelNode: ch.cassiamon.pluginapi.model.ModelNode, facetValue: String?) -> String?
) : ch.cassiamon.pluginapi.FacetSchema(facetName, enclosingConceptName, isOnlyCalculated)
