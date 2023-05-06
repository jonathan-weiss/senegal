package ch.cassiamon.pluginapi.extensions.inputsource.files

import ch.cassiamon.pluginapi.extensions.Extension
import ch.cassiamon.pluginapi.extensions.ExtensionInitialization
import ch.cassiamon.pluginapi.extensions.inputsource.InputSourceExtensionInitialization
import java.nio.file.Path

interface FilesInputSourceExtension: Extension, ExtensionInitialization, InputSourceExtensionInitialization {

    fun readFromFiles(files: Set<Path>)
}
