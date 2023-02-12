package ch.cassiamon.engine.schema.resolver

import ch.cassiamon.engine.schema.Schema
import ch.cassiamon.pluginapi.ConceptSchema
import ch.cassiamon.pluginapi.FacetSchema

object SchemaResolver {

    fun resolveSchema(conceptSchemas: Set<ConceptSchema>,
                      facetSchemas: Set<FacetSchema>): Schema {
        return Schema(setOf()) // TODO implement
    }
}
