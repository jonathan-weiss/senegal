package ch.cassiamon.engine

import ch.cassiamon.engine.filesystem.PhysicalFilesFileSystemAccess
import ch.cassiamon.engine.logger.JavaUtilLoggerFacade
import ch.cassiamon.engine.schema.finder.RegistrarFinder
import ch.cassiamon.engine.schema.registration.RegistrationApiDefaultImpl


fun main() {
    val registrars = RegistrarFinder.findAllRegistrars()
    println("Registrars: [${registrars.joinToString { it.projectName.name }}]")

    val registrationApi = RegistrationApiDefaultImpl(ProcessFacades())
    registrars.forEach { it.configure(registrationApi) }

    val schema = registrationApi.provideSchema()
    val templates = registrationApi.provideTemplates()

    println("Schema: $schema")
    println("Templates: $templates")
}
