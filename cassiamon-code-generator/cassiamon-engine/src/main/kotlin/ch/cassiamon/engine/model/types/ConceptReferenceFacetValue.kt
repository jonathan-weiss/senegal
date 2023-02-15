package ch.cassiamon.engine.model.types

import ch.cassiamon.pluginapi.model.ConceptIdentifier

data class ConceptReferenceFacetValue(
    val conceptReference: ConceptIdentifier
): FacetValue
