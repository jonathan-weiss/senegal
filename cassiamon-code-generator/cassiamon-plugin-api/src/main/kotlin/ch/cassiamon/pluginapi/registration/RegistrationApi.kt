package ch.cassiamon.pluginapi.registration

interface RegistrationApi {

    fun configureSchema(schemaRegistration: SchemaRegistration.() -> Unit)
    fun configureTemplates(templateRegistration: TemplatesRegistration.() -> Unit)
}
