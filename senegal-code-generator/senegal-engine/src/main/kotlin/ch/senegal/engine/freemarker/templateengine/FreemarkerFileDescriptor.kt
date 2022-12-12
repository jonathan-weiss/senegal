package ch.senegal.engine.freemarker.templateengine

import java.nio.file.Path

data class FreemarkerFileDescriptor(
    val targetFile: Path,
    val model: Map<String, Any?>,
    val templateClassPath: String,
)
