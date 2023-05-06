package ch.cassiamon.api.extensions.inputsource

import ch.cassiamon.api.filesystem.FileSystemAccess
import ch.cassiamon.api.registration.InputSourceDataCollector
import ch.cassiamon.api.schema.SchemaAccess

interface InputSourceExtensionInitialization {

    fun initializeInputSourceExtension(inputSourceDataCollector: InputSourceDataCollector, fileSystemAccess: FileSystemAccess)
    fun initializeSchema(schemaAccess: SchemaAccess)
}
