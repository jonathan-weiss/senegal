package ch.cassiamon.engine

import ch.cassiamon.engine.model.ModelInputData
import ch.cassiamon.engine.model.resolver.ModelResolver
import ch.cassiamon.engine.schema.resolver.SchemaResolver
import ch.cassiamon.pluginapi.ConceptSchema
import ch.cassiamon.pluginapi.FacetSchema
import ch.cassiamon.pluginapi.FacetFunction

class CassiamonProcess {



    fun createModelGraph() {

        // gather all concepts, facets, transformer and templateX by the plugin mechanism
        val conceptSchemas: Set<ConceptSchema> = emptySet()
        val facetSchemas: Set<FacetSchema> = emptySet()
        val facetFunctions: Set<FacetFunction> = emptySet()


        // resolve the raw concepts and facets to a resolved schema
        val schema = SchemaResolver.resolveSchema(conceptSchemas, facetSchemas)

        // create a XML schema to validate directly on xml
        // TODO call XML schema creator passing the schema

        // fill the schema model (?) with help of the resolved schema (e.g. by XML)
        // TODO read the XML file and fill it in a model
        val modelInputData: ModelInputData = ModelInputData(emptySet()) //TODO ... from XML

        // traverse whole model and transform (adapt/calculate/transform) the missing model values
        val modelGraph = ModelResolver.expandToModelGraphAndValidate(schema, modelInputData, facetFunctions)

        // TODO transform to TemplateNodes
        // TODO extract TemplateTargets
        // TODO write Templates


    }
}
