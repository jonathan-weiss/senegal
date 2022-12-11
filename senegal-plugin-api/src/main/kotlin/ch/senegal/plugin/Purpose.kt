package ch.senegal.plugin

import ch.senegal.plugin.model.ModelNode
import java.nio.file.Path

interface Purpose: Plugin {

    val purposeName: PurposeName

    val facets: Set<Facet>

    fun createTemplateTargets(modelNode: ModelNode, defaultOutputPath: Path): Set<TemplateTarget> = emptySet()
}
