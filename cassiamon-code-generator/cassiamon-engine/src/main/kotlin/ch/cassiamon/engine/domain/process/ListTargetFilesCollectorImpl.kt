package ch.cassiamon.engine.domain.process

import ch.cassiamon.api.registration.TargetFilesCollector
import java.nio.file.Path

class ListTargetFilesCollectorImpl: TargetFilesCollector, TargetFileCollectionProvider {
    private val targetFilesWithContent: MutableList<TargetFileWithContent> = mutableListOf()
    override fun addFile(targetFile: Path, fileContent: String) {
        targetFilesWithContent.add(TargetFileWithContent(targetFile, fileContent))
    }

    override fun getTargetFiles(): List<TargetFileWithContent> {
        return targetFilesWithContent
    }
}