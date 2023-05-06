package ch.cassiamon.api.registration

import ch.cassiamon.api.DomainUnitName


abstract class DomainUnit(val domainUnitName: DomainUnitName) {
    abstract fun configureDataCollector(inputSourceRegistration: InputSourceRegistrationApi)
    abstract fun configureSchema(registration: SchemaRegistrationApi)
    abstract fun configureTemplates(configureTemplatesRegistration: TemplatesRegistrationApi)

}
