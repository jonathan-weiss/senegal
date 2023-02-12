package ch.cassiamon.pluginapi

import java.nio.file.Path

interface Purpose: ch.cassiamon.pluginapi.Plugin {

    val purposeName: ch.cassiamon.pluginapi.PurposeName

    val facetSchemas: Set<ch.cassiamon.pluginapi.FacetSchema>

    fun createTemplateTargets(modelNode: ch.cassiamon.pluginapi.model.ModelNode, defaultOutputPath: Path): Set<ch.cassiamon.pluginapi.TemplateTarget> = emptySet()
}
