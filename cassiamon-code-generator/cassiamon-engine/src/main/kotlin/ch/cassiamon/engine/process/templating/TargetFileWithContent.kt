package ch.cassiamon.engine.process.templating

import java.nio.file.Path

data class TargetFileWithContent(val targetFile: Path, val fileContent: String)
