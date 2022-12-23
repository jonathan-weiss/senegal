package ch.senegal.engine.virtualfilesystem

import java.io.InputStream
import java.io.Writer
import java.nio.file.Path

interface VirtualFileSystem {

    fun fileAsInputStream(filePath: Path): InputStream
    fun createDirectory(directoryPath: Path)
    fun writeFile(filePath: Path, fileContent: String)
    fun getFileWriter(filePath: Path): Writer
}
