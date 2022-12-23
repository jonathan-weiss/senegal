package ch.senegal.engine.virtualfilesystem

import ch.senegal.engine.util.FileUtil
import java.io.InputStream
import java.io.Writer
import java.nio.file.Path

class PhysicalFilesVirtualFileSystem: VirtualFileSystem {

    override fun fileAsInputStream(filePath: Path): InputStream {
        FileUtil.checkFileReadable(filePath)
        return filePath.toFile().inputStream()
    }

    override fun createDirectory(directoryPath: Path) {
        directoryPath.toFile().mkdirs()
    }

    override fun writeFile(filePath: Path, fileContent: String) {
        filePath.toFile().writeText(fileContent)
    }

    override fun getFileWriter(filePath: Path): Writer {
        return java.io.FileWriter(filePath.toFile())
    }
}
