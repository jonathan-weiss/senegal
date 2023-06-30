package ch.cassiamon.engine.extension

import ch.cassiamon.api.extensions.ExtensionName
import ch.cassiamon.api.extensions.inputsource.files.FilesInputSourceExtension

interface ExtensionAccess {

    fun getFilesInputSourceExtension(extensionName: ExtensionName):FilesInputSourceExtension
}
