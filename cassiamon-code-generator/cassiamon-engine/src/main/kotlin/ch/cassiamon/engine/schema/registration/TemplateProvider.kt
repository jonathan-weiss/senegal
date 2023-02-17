package ch.cassiamon.engine.schema.registration

import ch.cassiamon.pluginapi.registration.TemplateFunction


interface TemplateProvider {

    fun provideTemplates(): List<TemplateFunction>
}
