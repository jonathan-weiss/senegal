package ch.cassiamon.engine.schema.types

import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName


class FacetForManualConceptReference(
    conceptName: ConceptName,
    facetName: FacetName,
    val referencedConceptName: ConceptName,
) : ManualFacet(
    conceptName = conceptName,
    facetName = facetName,
)

