package ch.cassiamon.engine.schema.types

import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName

class FacetForManuelText(
    conceptName: ConceptName,
    facetName: FacetName
) : ManualFacet(
    conceptName = conceptName,
    facetName = facetName,
)
