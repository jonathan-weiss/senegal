package ch.cassiamon.engine.schema.registration

import ch.cassiamon.pluginapi.template.TemplateRenderer


interface TemplateProvider {

    fun provideTemplates(): List<TemplateRenderer>
}
