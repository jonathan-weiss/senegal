package ch.cassiamon.pluginapi

import java.nio.file.Path

data class TemplateTarget(
    val targetFile: Path,
    val template: Template,
)
