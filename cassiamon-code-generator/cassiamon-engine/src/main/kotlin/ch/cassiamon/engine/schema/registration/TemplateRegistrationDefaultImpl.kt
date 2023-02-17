package ch.cassiamon.engine.schema.registration

import ch.cassiamon.pluginapi.registration.*
import ch.cassiamon.pluginapi.template.TemplateRenderer

class TemplateRegistrationDefaultImpl(val templateNodesProvider: TemplateNodesProviderDefaultImpl): TemplatesRegistration, TemplateProvider {
    private val templateRenderers: MutableList<TemplateRenderer> = mutableListOf()

    override fun newTemplate(templateFunction: TemplateFunction) {
        val templateRenderer = templateFunction.invoke(templateNodesProvider)
        templateRenderers.add(templateRenderer)
    }

    override fun provideTemplates(): List<TemplateRenderer> {
        return templateRenderers
    }
}
