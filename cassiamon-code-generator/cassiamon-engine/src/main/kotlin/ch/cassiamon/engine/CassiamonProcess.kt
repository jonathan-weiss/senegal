package ch.cassiamon.engine

import ch.cassiamon.engine.schema.finder.RegistrarFinder
import ch.cassiamon.engine.schema.registration.RegistrationApiDefaultImpl

class CassiamonProcess {



    fun createModelGraph() {

        // gather all concepts, facets, transformer and templateX by the plugin mechanism
        val registrars = RegistrarFinder.findAllRegistrars()
        val registrationApi = RegistrationApiDefaultImpl()
        registrars.forEach { it.configure(registrationApi) }

        // resolve the raw concepts and facets to a resolved schema
        val schema = registrationApi.provideSchema()

        println("Schema: $schema")



        // TODO



        // create a XML schema to validate directly on xml
        // TODO call XML schema creator passing the schema

        // fill the schema model (?) with help of the resolved schema (e.g. by XML)
        // TODO read the XML file and fill it in flat a modelInputData


        // traverse whole model and transform (adapt/calculate/transform) the missing model values
        // TODO impl

        // TODO transform to TemplateNodes
        // TODO extract TemplateTargets
        // TODO write Templates


    }
}
