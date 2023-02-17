package ch.cassiamon.engine

import ch.cassiamon.engine.schema.registration.RegistrationApiDefaultImpl
import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName
import ch.cassiamon.pluginapi.ProjectName
import ch.cassiamon.pluginapi.registration.Registrar
import ch.cassiamon.pluginapi.registration.RegistrationApi
import ch.cassiamon.pluginapi.template.helper.StringContentByteIterator
import ch.cassiamon.pluginapi.template.TargetGeneratedFileWithModel
import ch.cassiamon.pluginapi.template.TemplateRenderer
import java.nio.file.Paths

class TestRegistrar: Registrar(ProjectName.of("ExampleInternalProject")) {

    override fun configure(registrationApi: RegistrationApi) {
        val internalRegistrationApi = registrationApi as RegistrationApiDefaultImpl

        TestFixtures.createTestFixtureSchema(internalRegistrationApi)
        TestFixtures.createTestTemplates(internalRegistrationApi)

    }
}
