package ch.cassiamon.engine

import ch.cassiamon.engine.model.graph.ModelGraphCreator
import ch.cassiamon.engine.model.inputsource.ModelInputDataCollector
import ch.cassiamon.pluginapi.model.ConceptIdentifier
import ch.cassiamon.engine.model.types.TextFacetValue
import ch.cassiamon.engine.schema.finder.RegistrarFinder
import ch.cassiamon.engine.schema.registration.SchemaRegistrationApiDefaultImpl
import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName

class CassiamonProcess {



    fun createModelGraph() {

        // gather all concepts, facets, transformer and templateX by the plugin mechanism
        val registrars = RegistrarFinder.findAllRegistrars()
        val registrationApi = SchemaRegistrationApiDefaultImpl()
        registrars.forEach { it.configure(registrationApi) }

        // resolve the raw concepts and facets to a resolved schema
        val schema = registrationApi.provideSchema()

        println("Schema: $schema")



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
        val modelGraph = ModelGraphCreator.calculateGraph(schema, modelInputData)

        // TODO transform to TemplateNodes
        // TODO extract TemplateTargets
        // TODO write Templates


    }
}
