package ch.senegal.plugin

import java.nio.file.Path

data class TemplateTarget(
    val targetFile: Path,
    val templateClasspath: String,
)
