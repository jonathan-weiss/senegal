package ch.cassiamon.engine

import ch.cassiamon.engine.inputsource.ModelInputDataCollector
import ch.cassiamon.pluginapi.model.ConceptIdentifier
import ch.cassiamon.engine.schema.finder.RegistrarFinder
import ch.cassiamon.engine.schema.registration.RegistrationApiDefaultImpl
import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.model.facets.*

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

        modelInputDataCollector.newConceptData(
            conceptName = ConceptName.of("DatabaseTable"),
            conceptIdentifier = ConceptIdentifier.of("Person"),
            parentConceptIdentifier = null,
        )
            .addFacetValue(MandatoryTextInputFacet.of("TableName").facetValue("Person"))
            .addFacetValue(MandatoryNumberInputFacet.of("TableSize").facetValue(2323))
            .addFacetValue(OptionalNumberInputFacet.of("TableSize").facetValue(null))
            .addFacetValue(OptionalNumberInputFacet.of("TableSize").facetValue(2345))
            .attach()



        val modelInputData = modelInputDataCollector.provideModelInputData()
        val x = modelInputData.entries[0].inputFacetValueAccess.facetValue(MandatoryNumberInputFacet.of("TableSize"))
        val y = modelInputData.entries[0].inputFacetValueAccess.facetValue(MandatoryTextInputFacet.of("TableSize"))
        val z = modelInputData.entries[0].inputFacetValueAccess.facetValue(OptionalNumberInputFacet.of("TableSize"))
        val a = modelInputData.entries[0].inputFacetValueAccess.facetValue(MandatoryTextInputAndTemplateFacet.of("TableSize"))
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
