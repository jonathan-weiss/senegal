package ch.cassiamon.engine.extension

import ch.cassiamon.api.extensions.ExtensionName
import ch.cassiamon.api.extensions.inputsource.files.FilesInputSourceExtension
import ch.cassiamon.api.extensions.template.ClasspathTemplateExtension

interface ExtensionAccess {

    fun getClasspathTemplateExtension(extensionName: ExtensionName):ClasspathTemplateExtension

    fun getFilesInputSourceExtension(extensionName: ExtensionName):FilesInputSourceExtension
}
