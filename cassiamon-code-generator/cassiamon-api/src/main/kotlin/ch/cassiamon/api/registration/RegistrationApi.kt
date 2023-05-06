package ch.cassiamon.api.registration

interface RegistrationApi {

    fun configureDataCollector(inputSourceRegistration: InputSourceRegistration.() -> Unit)
    fun configureSchema(schemaRegistration: SchemaRegistration.() -> Unit)
    fun configureTemplates(templateRegistration: TemplatesRegistration.() -> Unit)
}
