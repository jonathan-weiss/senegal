package ch.cassiamon.api.registration

import ch.cassiamon.api.model.ConceptModelGraph
import ch.cassiamon.api.template.TemplateRenderer

fun interface TemplateFunction<T> {

    fun invoke(conceptModelGraph: ConceptModelGraph): TemplateRenderer<T>
}
