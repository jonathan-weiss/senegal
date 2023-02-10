package ch.cassiamon.pluginapi

import ch.cassiamon.pluginapi.model.ModelNode
import java.nio.file.Path

interface Purpose: ch.cassiamon.pluginapi.Plugin {

    val purposeName: ch.cassiamon.pluginapi.PurposeName

    val facets: Set<ch.cassiamon.pluginapi.Facet>

    fun createTemplateTargets(modelNode: ch.cassiamon.pluginapi.model.ModelNode, defaultOutputPath: Path): Set<ch.cassiamon.pluginapi.TemplateTarget> = emptySet()
}
