package ch.cassiamon.engine.model.resolver

import ch.cassiamon.engine.model.ModelInputData
import ch.cassiamon.engine.model.ModelGraph
import ch.cassiamon.engine.schema.Schema
import ch.cassiamon.pluginapi.FacetFunction

object ModelResolver {

    fun expandToModelGraphAndValidate(schema: Schema, modelInputData: ModelInputData, facetFunctions: Set<FacetFunction>): ModelGraph {
        return ModelGraph()
    }
}
