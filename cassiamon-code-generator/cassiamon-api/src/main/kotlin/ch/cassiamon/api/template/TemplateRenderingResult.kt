package ch.cassiamon.api.template

import java.nio.file.Path

data class TemplateRenderingResult(
    val targetFile: Path,
    val byteIterator: ByteIterator,
)
