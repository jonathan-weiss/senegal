package ch.cassiamon.pluginapi.registration

interface RegistrationApi {

    fun collectData(inputSourceRegistration: (dataCollector: InputSourceDataCollector) -> Unit)
    fun configureSchema(schemaRegistration: SchemaRegistration.() -> Unit)
    fun configureTemplates(templateRegistration: TemplatesRegistration.() -> Unit)
}
