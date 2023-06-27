package ch.cassiamon.api.registration

import java.nio.file.Path

interface TargetFilesCollector {
    fun addFile(targetFile: Path, fileContent: String)
}
