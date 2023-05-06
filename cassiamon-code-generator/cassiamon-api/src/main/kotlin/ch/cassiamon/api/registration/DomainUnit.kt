package ch.cassiamon.api.registration

import ch.cassiamon.api.DomainUnitName


interface DomainUnit {
    val domainUnitName: DomainUnitName
    fun configureDataCollector(registration: InputSourceRegistrationApi)
    fun configureSchema(registration: SchemaRegistrationApi)
    fun configureTemplates(registration: TemplatesRegistrationApi)

}
