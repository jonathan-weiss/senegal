package ch.cassiamon.api.extensions.inputsource

import ch.cassiamon.api.filesystem.FileSystemAccess
import ch.cassiamon.api.schema.SchemaAccess

interface InputSourceExtensionInitialization {

    fun initializeInputSourceExtension(conceptAndFacetDataCollector: ConceptAndFacetDataCollector, fileSystemAccess: FileSystemAccess)
    fun initializeSchema(schemaAccess: SchemaAccess)
}
