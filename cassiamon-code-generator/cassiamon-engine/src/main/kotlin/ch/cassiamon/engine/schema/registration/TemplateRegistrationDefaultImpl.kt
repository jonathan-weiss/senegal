package ch.cassiamon.engine.schema.registration

import ch.cassiamon.pluginapi.registration.*

class TemplateRegistrationDefaultImpl(): TemplatesRegistration, TemplateProvider {
    private val templateFunctions: MutableList<TemplateFunction> = mutableListOf()

    override fun newTemplate(templateFunction: TemplateFunction) {
        templateFunctions.add(templateFunction)
    }

    override fun provideTemplates(): List<TemplateFunction> {
        return templateFunctions
    }
}
