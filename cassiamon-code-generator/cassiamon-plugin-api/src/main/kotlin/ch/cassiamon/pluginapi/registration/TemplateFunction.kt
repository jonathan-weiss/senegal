package ch.cassiamon.pluginapi.registration

import ch.cassiamon.pluginapi.template.TemplateNodesProvider
import ch.cassiamon.pluginapi.template.TemplateRenderer

fun interface TemplateFunction {

    fun invoke(templateNodesProvider: TemplateNodesProvider): TemplateRenderer
}
