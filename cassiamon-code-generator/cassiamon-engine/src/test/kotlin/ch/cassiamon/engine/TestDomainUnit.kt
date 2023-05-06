package ch.cassiamon.engine

import ch.cassiamon.engine.schema.registration.RegistrationApiDefaultImpl
import ch.cassiamon.pluginapi.DomainUnitName
import ch.cassiamon.pluginapi.registration.DomainUnit
import ch.cassiamon.pluginapi.registration.RegistrationApi

class TestDomainUnit: DomainUnit(DomainUnitName.of("ExampleInternalProject")) {

    override fun configure(registrationApi: RegistrationApi) {
        val internalRegistrationApi = registrationApi as RegistrationApiDefaultImpl

        TestFixtures.createTestFixtureSchema(internalRegistrationApi)
        TestFixtures.createTestTemplates(internalRegistrationApi)

    }
}
