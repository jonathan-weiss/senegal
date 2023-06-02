package ch.cassiamon.api.template

import java.nio.file.Path

data class TargetGeneratedFileWithModel<out T>(
    val targetFile: Path,
    val model: List<T>
)
