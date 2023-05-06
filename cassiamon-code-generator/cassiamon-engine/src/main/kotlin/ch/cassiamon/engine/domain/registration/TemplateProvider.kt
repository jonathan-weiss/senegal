package ch.cassiamon.engine.domain.registration

import ch.cassiamon.pluginapi.registration.TemplateFunction


interface TemplateProvider {

    fun provideTemplates(): List<TemplateFunction>
}
