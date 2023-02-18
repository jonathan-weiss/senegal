package ch.cassiamon.engine

import ch.cassiamon.engine.model.graph.ModelCalculator
import ch.cassiamon.engine.model.inputsource.ModelInputDataCollector
import ch.cassiamon.pluginapi.model.ConceptIdentifier
import ch.cassiamon.engine.model.types.TextFacetValue
import ch.cassiamon.engine.schema.finder.RegistrarFinder
import ch.cassiamon.engine.schema.registration.RegistrationApiDefaultImpl
import ch.cassiamon.engine.template.TemplateNodesProviderDefaultImpl
import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName
import ch.cassiamon.pluginapi.template.TemplateRenderer

class CassiamonProcess {



    fun createModelGraph() {

        // gather all concepts, facets, transformer and templateX by the plugin mechanism
        val registrars = RegistrarFinder.findAllRegistrars()
        val registrationApi = RegistrationApiDefaultImpl()
        registrars.forEach { it.configure(registrationApi) }

        // resolve the raw concepts and facets to a resolved schema
        val schema = registrationApi.provideSchema()
        val templates = registrationApi.provideTemplates()

        println("Schema: $schema")
        println("Templates: $templates")



        // TODO



        // create a XML schema to validate directly on xml
        // TODO call XML schema creator passing the schema

        // fill the schema model (?) with help of the resolved schema (e.g. by XML)
        // TODO read the XML file and fill it in flat a modelInputData

        val modelInputDataCollector = ModelInputDataCollector()
        modelInputDataCollector.attachConceptData(
            conceptName = ConceptName.of("DatabaseTable"),
            conceptIdentifier = ConceptIdentifier.of("Person"),
            parentConceptIdentifier = null,
            facetValues = arrayOf(Pair(FacetName.of("TableName"), TextFacetValue("Person"))),
        )

        val modelInputData = modelInputDataCollector.provideModelInputData()


        // traverse whole model and transform (adapt/calculate/transform) the missing model values
        val modelGraph = ModelCalculator.calculateModel(schema, modelInputData)

        val templateNodesProvider = TemplateNodesProviderDefaultImpl(modelGraph)

        // TODO transform to TemplateNodes (by implementing interface

        // TODO write Templates
        val templateRenderers = templates.map { template -> template.invoke(templateNodesProvider) }.toSet()

        templateRenderers.forEach { templateRenderer: TemplateRenderer ->
            templateRenderer.targetFilesWithModel.forEach { targetGeneratedFileWithModel ->
                val byteIterator = templateRenderer.templateRenderer(targetGeneratedFileWithModel)
                // TODO write file
            }
        }


    }
}
