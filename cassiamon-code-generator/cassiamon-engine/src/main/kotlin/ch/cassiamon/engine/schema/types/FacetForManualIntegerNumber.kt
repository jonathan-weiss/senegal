package ch.cassiamon.engine.schema.types

import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName

class FacetForManualIntegerNumber(
    conceptName: ConceptName,
    facetName: FacetName,
) : ManualFacet(
    conceptName = conceptName,
    facetName = facetName,
)
