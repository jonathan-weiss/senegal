package ch.cassiamon.pluginapi

class IntegerFacetSchema(
    facetName: ch.cassiamon.pluginapi.FacetName,
    enclosingConceptName: ch.cassiamon.pluginapi.ConceptName,
    isOnlyCalculated: Boolean,
    val enhanceFacetValue: (modelNode: ch.cassiamon.pluginapi.model.ModelNode, facetValue: Int?) -> Int?
) : ch.cassiamon.pluginapi.FacetSchema(facetName, enclosingConceptName, isOnlyCalculated)
