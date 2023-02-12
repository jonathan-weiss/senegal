package ch.cassiamon.engine

import ch.cassiamon.engine.schema.finder.RegistrarFinder
import ch.cassiamon.engine.schema.registration.RegistrationApiDefaultImpl


fun main() {
    val registrars = RegistrarFinder.findAllRegistrars()
    println("Registrars: [${registrars.joinToString { it.projectName.name }}]")

    val registrationApi = RegistrationApiDefaultImpl()
    registrars.forEach { it.configure(registrationApi) }


}
