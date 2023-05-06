package ch.cassiamon.pluginapi.extensions.inputsource

import ch.cassiamon.pluginapi.filesystem.FileSystemAccess
import ch.cassiamon.pluginapi.registration.InputSourceDataCollector

interface InputSourceExtensionInitialization {

    fun initializeInputSourceExtension(inputSourceDataCollector: InputSourceDataCollector, fileSystemAccess: FileSystemAccess)
}
