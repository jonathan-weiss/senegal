package ch.senegal.engine.template

import ch.senegal.plugin.Template
import java.nio.file.Path

data class TemplateTargetWithModel(
    val targetFile: Path,
    val model: Map<String, Any?>,
    val template: Template,
)
