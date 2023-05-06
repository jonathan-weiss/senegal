package ch.cassiamon.api.extensions.inputsource.files

import ch.cassiamon.api.extensions.Extension
import ch.cassiamon.api.extensions.ExtensionInitialization
import ch.cassiamon.api.extensions.inputsource.InputSourceExtensionInitialization
import java.nio.file.Path

interface FilesInputSourceExtension: Extension, ExtensionInitialization, InputSourceExtensionInitialization {

    fun readFromFiles(files: Set<Path>)
}
