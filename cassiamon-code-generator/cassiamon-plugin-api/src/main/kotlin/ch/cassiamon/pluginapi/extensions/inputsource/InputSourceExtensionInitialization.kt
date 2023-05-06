package ch.cassiamon.pluginapi.extensions.inputsource

import ch.cassiamon.pluginapi.filesystem.FileSystemAccess
import ch.cassiamon.pluginapi.registration.InputSourceDataCollector
import ch.cassiamon.pluginapi.schema.SchemaAccess

interface InputSourceExtensionInitialization {

    fun initializeInputSourceExtension(inputSourceDataCollector: InputSourceDataCollector, fileSystemAccess: FileSystemAccess)
    fun initializeSchema(schema: SchemaAccess)
}
