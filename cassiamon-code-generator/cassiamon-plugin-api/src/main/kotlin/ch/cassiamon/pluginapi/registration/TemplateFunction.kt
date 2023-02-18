package ch.cassiamon.pluginapi.registration

import ch.cassiamon.pluginapi.model.ConceptModelGraph
import ch.cassiamon.pluginapi.template.TemplateRenderer

fun interface TemplateFunction {

    fun invoke(conceptModelGraph: ConceptModelGraph): TemplateRenderer
}
