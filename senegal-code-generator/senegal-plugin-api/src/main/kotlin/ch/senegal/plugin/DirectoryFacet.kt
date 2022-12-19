package ch.senegal.plugin

import ch.senegal.plugin.model.ModelNode
import java.nio.file.Path

class DirectoryFacet(
    facetName: FacetName,
    enclosingConceptName: ConceptName,
    isOnlyCalculated: Boolean,
    val enhanceFacetValue: (modelNode: ModelNode, facetValue: Path?) -> Path?
) : Facet(facetName, enclosingConceptName, isOnlyCalculated)
