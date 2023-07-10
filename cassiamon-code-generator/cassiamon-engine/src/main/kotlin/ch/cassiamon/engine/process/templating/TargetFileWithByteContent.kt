package ch.cassiamon.engine.process.templating

import ch.cassiamon.api.process.templating.TargetFileWithContent
import java.nio.file.Path

class TargetFileWithByteContent(override val targetFile: Path, override val fileContent: ByteArray): TargetFileWithContent
