package ch.cassiamon.api.registration

import ch.cassiamon.api.DomainUnitName


interface DomainUnit {
    val domainUnitName: DomainUnitName
    fun configureDataCollector(inputSourceRegistration: InputSourceRegistrationApi)
    fun configureSchema(registration: SchemaRegistrationApi)
    fun configureTemplates(configureTemplatesRegistration: TemplatesRegistrationApi)

}
