package ch.cassiamon.api.process.templating

import java.nio.file.Path

interface TargetFilesCollector {
    fun addFile(targetFile: Path, fileContent: String)
}
