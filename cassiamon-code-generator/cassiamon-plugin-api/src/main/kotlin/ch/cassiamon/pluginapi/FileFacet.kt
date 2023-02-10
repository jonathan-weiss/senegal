package ch.cassiamon.pluginapi

import ch.cassiamon.pluginapi.model.ModelNode
import java.nio.file.Path

class FileFacet(
    facetName: ch.cassiamon.pluginapi.FacetName,
    enclosingConceptName: ch.cassiamon.pluginapi.ConceptName,
    isOnlyCalculated: Boolean,
    val enhanceFacetValue: (modelNode: ch.cassiamon.pluginapi.model.ModelNode, facetValue: Path?) -> Path?
) : ch.cassiamon.pluginapi.Facet(facetName, enclosingConceptName, isOnlyCalculated)
