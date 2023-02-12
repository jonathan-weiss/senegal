package ch.cassiamon.pluginapi.registration

import ch.cassiamon.pluginapi.ProjectName


abstract class Registrar(val projectName: ProjectName) {
    abstract fun configure(registrationApi: RegistrationApi)

}
