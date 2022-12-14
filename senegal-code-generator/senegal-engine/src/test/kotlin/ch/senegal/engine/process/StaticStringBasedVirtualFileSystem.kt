package ch.senegal.engine.process

import ch.senegal.engine.virtualfilesystem.VirtualFileSystem
import java.io.Closeable
import java.io.IOException
import java.io.InputStream
import java.io.StringWriter
import java.io.Writer
import java.nio.file.Path
import kotlin.io.path.pathString

class StaticStringBasedVirtualFileSystem(private val classpathResources: Map<String, String>,
                                         private val files: Map<Path, String>): VirtualFileSystem {
    private val charset = Charsets.UTF_8

    private val closeables = mutableListOf<Pair<String, Closeable>>()
    private val writtenFiles = mutableMapOf<Path, String>()
    private val writtenFileWriter = mutableMapOf<Path, Writer>()

    private fun <T: Closeable> registerClosable(name: String, closable: T): T {
        closeables.add(Pair(name, closable))
        return closable
    }

    override fun classpathResourceAsInputStream(classpathResource: String): InputStream {
        return classpathResources[classpathResource]
            ?.let { registerClosable(classpathResource, it.byteInputStream(charset)) }
            ?: throw IllegalArgumentException("Resource with name '${classpathResource}' not found.")

    }
    override fun fileAsInputStream(filePath: Path): InputStream {
        val normalizedPath = filePath.normalize()
        return (files[normalizedPath] ?: writtenFiles[normalizedPath])
            ?.let { registerClosable(filePath.pathString, stringAsInputStream(it)) }
            ?: throw IllegalArgumentException("File with name '${normalizedPath}' not found.")
    }

    private fun stringAsInputStream(stringValue: String): InputStream {
        return stringValue.byteInputStream(charset)
    }

    override fun createDirectory(directoryPath: Path) {
        // do nothing
    }

    override fun writeFile(filePath: Path, fileContent: String) {
        val normalizedFilePath = filePath.normalize()
        if(writtenFiles.containsKey(normalizedFilePath)) {
            throw IllegalStateException("File with filepath $normalizedFilePath has already been written.")
        }
        writtenFiles[normalizedFilePath] = fileContent
    }

    override fun getFileWriter(filePath: Path): Writer {
        val normalizedFilePath = filePath.normalize()
        if(writtenFileWriter.containsKey(normalizedFilePath)) {
            throw IllegalStateException("File writer with filepath $normalizedFilePath has already been requested.")
        }
        val stringWriter = registerClosable(filePath.pathString, StringWriter())
        writtenFileWriter[normalizedFilePath] = stringWriter

        return stringWriter
    }

    override fun close() {
        // do nothing
    }

    fun fetchFileContent(path: Path): String {
        return writtenFilesMap()[path] ?: throw IllegalStateException("No entry with key '$path'.")
    }

    private fun writtenFilesMap(): Map<Path, String> {
        val resultMap: MutableMap<Path, String> = mutableMapOf()
        resultMap.putAll(writtenFiles)
        resultMap.putAll(writtenFileWriter.mapValues { it.value.toString() })
        return resultMap
    }
}
