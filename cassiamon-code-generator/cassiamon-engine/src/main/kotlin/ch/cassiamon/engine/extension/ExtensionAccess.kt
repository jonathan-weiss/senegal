package ch.cassiamon.engine.extension

import ch.cassiamon.pluginapi.extensions.ExtensionName
import ch.cassiamon.pluginapi.extensions.inputsource.files.FilesInputSourceExtension
import ch.cassiamon.pluginapi.extensions.template.ClasspathTemplateExtension

interface ExtensionAccess {

    fun getClasspathTemplateExtension(extensionName: ExtensionName):ClasspathTemplateExtension

    fun getFilesInputSourceExtension(extensionName: ExtensionName):FilesInputSourceExtension
}
