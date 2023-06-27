package ch.cassiamon.engine.domain.process

import java.nio.file.Path

data class TargetFileWithContent(val targetFile: Path, val fileContent: String)
