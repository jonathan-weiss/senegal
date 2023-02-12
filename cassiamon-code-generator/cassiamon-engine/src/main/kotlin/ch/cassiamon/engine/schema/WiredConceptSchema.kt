package ch.cassiamon.engine.schema

import ch.cassiamon.pluginapi.ConceptSchema
import ch.cassiamon.pluginapi.FacetSchema

data class WiredConceptSchema(
    val conceptSchema: ConceptSchema,
    val facetSchemas: Set<WiredFacetSchema>
)
