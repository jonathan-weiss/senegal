package ch.cassiamon.pluginapi

class BooleanFacetSchema(
    facetName: ch.cassiamon.pluginapi.FacetName,
    enclosingConceptName: ch.cassiamon.pluginapi.ConceptName,
    isOnlyCalculated: Boolean,
    val enhanceFacetValue: (modelNode: ch.cassiamon.pluginapi.model.ModelNode, facetValue: Boolean?) -> Boolean?
) : ch.cassiamon.pluginapi.FacetSchema(facetName, enclosingConceptName, isOnlyCalculated)
