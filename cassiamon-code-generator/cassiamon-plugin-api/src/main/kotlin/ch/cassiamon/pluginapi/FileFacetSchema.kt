package ch.cassiamon.pluginapi

import java.nio.file.Path

class FileFacetSchema(
    facetName: ch.cassiamon.pluginapi.FacetName,
    enclosingConceptName: ch.cassiamon.pluginapi.ConceptName,
    isOnlyCalculated: Boolean,
    val enhanceFacetValue: (modelNode: ch.cassiamon.pluginapi.model.ModelNode, facetValue: Path?) -> Path?
) : ch.cassiamon.pluginapi.FacetSchema(facetName, enclosingConceptName, isOnlyCalculated)
