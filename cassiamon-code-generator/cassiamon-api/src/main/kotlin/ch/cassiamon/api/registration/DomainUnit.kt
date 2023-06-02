package ch.cassiamon.api.registration

import ch.cassiamon.api.DomainUnitName
import ch.cassiamon.api.parameter.ParameterAccess


interface DomainUnit {
    val domainUnitName: DomainUnitName
    fun provideParameters(parameterAccess: ParameterAccess) {
        // overwrite if parameterAccess is needed
    }
    fun configureDataCollector(registration: InputSourceRegistrationApi)
    fun configureSchema(registration: SchemaRegistrationApi)
    fun configureTemplates(registration: TemplatesRegistrationApi)

}
