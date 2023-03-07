package ch.cassiamon.engine.schema.registration

import ch.cassiamon.engine.schema.Schema
import ch.cassiamon.pluginapi.registration.*

class RegistrationApiDefaultImpl: RegistrationApi, SchemaProvider, TemplateProvider {
    private val schemaRegistrationImpl = SchemaRegistrationDefaultImpl()
    private val templateRegistrationImpl = TemplateRegistrationDefaultImpl()

    override fun configureSchema(schemaRegistration: SchemaRegistration.() -> Unit) {
        schemaRegistration(schemaRegistrationImpl)
    }

    override fun configureTemplates(templateRegistration: TemplatesRegistration.() -> Unit) {
        templateRegistration(templateRegistrationImpl)
    }

    override fun provideSchema(): Schema {
        return schemaRegistrationImpl.provideSchema()
    }

    override fun provideTemplates(): List<TemplateFunction> {
        return templateRegistrationImpl.provideTemplates()
    }
}
