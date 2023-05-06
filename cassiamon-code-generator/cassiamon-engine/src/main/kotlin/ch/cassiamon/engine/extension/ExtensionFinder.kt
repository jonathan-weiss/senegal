package ch.cassiamon.engine.extension

import ch.cassiamon.pluginapi.extensions.inputsource.files.FilesInputSourceExtension
import ch.cassiamon.pluginapi.extensions.template.ClasspathTemplateExtension
import java.util.*

object ExtensionFinder {

    fun findAllClasspathTemplateExtensions(): List<ClasspathTemplateExtension> {
        val extensionServiceLoader: ServiceLoader<ClasspathTemplateExtension> = ServiceLoader.load(ClasspathTemplateExtension::class.java)

        return extensionServiceLoader.toList()
    }

    fun findAllFilesInputSourceExtensions(): List<FilesInputSourceExtension> {
        val extensionServiceLoader: ServiceLoader<FilesInputSourceExtension> = ServiceLoader.load(FilesInputSourceExtension::class.java)

        return extensionServiceLoader.toList()
    }

}
