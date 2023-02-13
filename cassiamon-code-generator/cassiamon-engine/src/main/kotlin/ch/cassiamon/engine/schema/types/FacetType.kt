package ch.cassiamon.engine.schema.types

import ch.cassiamon.pluginapi.registration.GraphNode
import ch.cassiamon.pluginapi.registration.types.ConceptReferenceFacetTransformationFunction
import ch.cassiamon.pluginapi.registration.types.FacetTransformationFunction
import ch.cassiamon.pluginapi.registration.types.IntegerNumberFacetTransformationFunction
import ch.cassiamon.pluginapi.registration.types.TextFacetTransformationFunction

enum class FacetType() {
    TEXT,
    INTEGER_NUMBER,
    CONCEPT_REFERENCE,
}

