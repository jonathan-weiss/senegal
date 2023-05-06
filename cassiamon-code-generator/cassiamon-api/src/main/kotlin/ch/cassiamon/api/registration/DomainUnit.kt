package ch.cassiamon.api.registration

import ch.cassiamon.api.DomainUnitName


abstract class DomainUnit(val domainUnitName: DomainUnitName) {
    abstract fun configure(registrationApi: RegistrationApi)

}
