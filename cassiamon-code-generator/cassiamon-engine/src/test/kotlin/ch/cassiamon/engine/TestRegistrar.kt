package ch.cassiamon.engine

import ch.cassiamon.engine.schema.registration.RegistrationApiDefaultImpl
import ch.cassiamon.pluginapi.ProjectName
import ch.cassiamon.pluginapi.registration.Registrar
import ch.cassiamon.pluginapi.registration.RegistrationApi

class TestRegistrar: Registrar(ProjectName.of("ExampleInternalProject")) {

    override fun configure(registrationApi: RegistrationApi) {
        val internalRegistrationApi = registrationApi as RegistrationApiDefaultImpl

        TestFixtures.createTestFixtureSchema(internalRegistrationApi)
        TestFixtures.createTestTemplates(internalRegistrationApi)

    }
}
