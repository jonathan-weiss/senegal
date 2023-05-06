package ch.cassiamon.engine.domain.registration

import ch.cassiamon.api.registration.TemplateFunction


interface TemplateProvider {

    fun provideTemplates(): List<TemplateFunction>
}
