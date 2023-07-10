package ch.cassiamon.engine.process.templating

import ch.cassiamon.api.process.templating.TargetFileWithContent
import ch.cassiamon.api.process.templating.TargetFilesCollector
import java.nio.file.Path

class ListTargetFilesCollectorImpl: TargetFilesCollector {
    private val targetFilesWithContent: MutableList<TargetFileWithByteContent> = mutableListOf()
    override fun addFile(targetFile: Path, fileContent: String) {
        addFile(targetFile, fileContent.toByteArray(charset = Charsets.UTF_8))
    }

    override fun addFile(targetFile: Path, fileByteContent: ByteArray) {
        targetFilesWithContent.add(TargetFileWithByteContent(targetFile, fileByteContent))
    }

    fun getTargetFiles(): List<TargetFileWithContent> {
        return targetFilesWithContent
    }
}
