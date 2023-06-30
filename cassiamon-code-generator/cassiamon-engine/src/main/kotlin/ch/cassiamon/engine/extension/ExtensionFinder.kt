package ch.cassiamon.engine.extension

import ch.cassiamon.api.extensions.inputsource.files.FilesInputSourceExtension
import java.util.*

object ExtensionFinder {

    fun findAllFilesInputSourceExtensions(): List<FilesInputSourceExtension> {
        val extensionServiceLoader: ServiceLoader<FilesInputSourceExtension> = ServiceLoader.load(FilesInputSourceExtension::class.java)

        return extensionServiceLoader.toList()
    }

}
