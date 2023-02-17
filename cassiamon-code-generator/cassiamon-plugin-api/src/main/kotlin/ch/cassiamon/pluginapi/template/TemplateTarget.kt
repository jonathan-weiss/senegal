package ch.cassiamon.pluginapi.template

import java.nio.file.Path

data class TemplateTarget(
    val targetFile: Path,
    val targetFileContent: ByteIterator,
)
