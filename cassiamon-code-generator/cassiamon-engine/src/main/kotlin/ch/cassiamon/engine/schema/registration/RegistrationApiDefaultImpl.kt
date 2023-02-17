package ch.cassiamon.engine.schema.registration

import ch.cassiamon.engine.schema.types.Schema
import ch.cassiamon.pluginapi.registration.*
import ch.cassiamon.pluginapi.template.TemplateRenderer

class RegistrationApiDefaultImpl: RegistrationApi, SchemaProvider, TemplateProvider {
    private val schemaRegistrationImpl = SchemaRegistrationDefaultImpl()
    private val templateNodesProviderImpl = TemplateNodesProviderDefaultImpl()
    private val templateRegistrationImpl = TemplateRegistrationDefaultImpl(templateNodesProviderImpl)

    override fun configureSchema(schemaRegistration: SchemaRegistration.() -> Unit) {
        schemaRegistration(schemaRegistrationImpl)
    }

    override fun configureTemplates(templateRegistration: TemplatesRegistration.() -> Unit) {
        templateRegistration(templateRegistrationImpl)
    }

    override fun provideSchema(): Schema {
        return schemaRegistrationImpl.provideSchema()
    }

    override fun provideTemplates(): List<TemplateRenderer> {
        return templateRegistrationImpl.provideTemplates()
    }
}
