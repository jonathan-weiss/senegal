package ch.cassiamon.pluginapi.template

import java.nio.file.Path

data class TargetGeneratedFileWithModel(
    val targetFile: Path,
    val model: TemplateNodeBag
)
