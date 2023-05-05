package ch.cassiamon.engine

import ch.cassiamon.engine.inputsource.ModelInputDataCollector
import ch.cassiamon.pluginapi.model.ConceptIdentifier
import ch.cassiamon.engine.schema.registration.RegistrationApiDefaultImpl
import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.model.facets.*
import ch.cassiamon.pluginapi.registration.Registrar

class EngineProcess(private val registrars: List<Registrar>, private val engineProcessHelpers: EngineProcessHelpers) {



    fun runProcess() {
        // gather all concepts, facets, transformer and templateX by the plugin mechanism
        val registrationApi = RegistrationApiDefaultImpl(engineProcessHelpers)
        registrars.forEach { it.configure(registrationApi) }

        // resolve the raw concepts and facets to a resolved schema
        val schema = registrationApi.provideSchema()
        val templates = registrationApi.provideTemplates()
        val modelInputData = registrationApi.provideModelInputData()

        println("Schema: $schema")
        println("Templates: $templates")
        println("InputData: $modelInputData")

        // val b = modelInputData.entries[0].inputFacetValueAccess.facetValue(MandatoryTextTemplateFacet.of("TableSize"))


//        // traverse whole model and transform (adapt/calculate/transform) the missing model values
//        val modelGraph = ModelCalculator.calculateModel(schema, modelInputData)
//
//        val templateNodesProvider = ConceptModelGraphDefaultImpl(modelGraph)
//
//        // TODO transform to TemplateNodes (by implementing interface
//
//        // TODO write Templates
//        val templateRenderers = templates.map { template -> template.invoke(templateNodesProvider) }.toSet()
//
//        templateRenderers.forEach { templateRenderer: TemplateRenderer ->
//            templateRenderer.targetFilesWithModel.forEach { targetGeneratedFileWithModel ->
//                val byteIterator = templateRenderer.templateRenderer(targetGeneratedFileWithModel)
//                // TODO write file
//            }
//        }


    }
}
