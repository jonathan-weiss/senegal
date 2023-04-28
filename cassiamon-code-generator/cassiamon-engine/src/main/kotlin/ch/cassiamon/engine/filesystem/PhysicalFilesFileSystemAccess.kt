package ch.cassiamon.engine.filesystem

import ch.cassiamon.pluginapi.filesystem.FileSystemAccess
import java.io.InputStream
import java.io.Writer
import java.nio.file.Path

class PhysicalFilesFileSystemAccess: FileSystemAccess {

    override fun classpathResourceAsInputStream(classpathResource: String): InputStream {
        return this.javaClass.getResourceAsStream(classpathResource)
            ?: throw IllegalArgumentException("Resource with name '${classpathResource}' not found.")

    }
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

    override fun close() {
        // nothing to do
    }
}
