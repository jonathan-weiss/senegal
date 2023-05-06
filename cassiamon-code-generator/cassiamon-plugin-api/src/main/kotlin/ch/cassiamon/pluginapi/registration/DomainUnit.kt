package ch.cassiamon.pluginapi.registration

import ch.cassiamon.pluginapi.DomainUnitName


abstract class DomainUnit(val domainUnitName: DomainUnitName) {
    abstract fun configure(registrationApi: RegistrationApi)

}
