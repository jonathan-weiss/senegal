package ch.cassiamon.api.datacollection.extensions

import ch.cassiamon.api.extensions.Extension
import ch.cassiamon.api.filesystem.FileSystemAccess
import ch.cassiamon.api.logger.LoggerFacade
import ch.cassiamon.api.parameter.ParameterAccess
import ch.cassiamon.api.schema.SchemaAccess
import java.nio.file.Path

interface DataCollectionFromFilesExtension: Extension {

    fun initializeDataCollectionExtension(
        loggerFacade: LoggerFacade,
        parameterAccess: ParameterAccess,
        fileSystemAccess: FileSystemAccess,
        schemaAccess: SchemaAccess,
        conceptAndFacetDataCollector: ConceptAndFacetDataCollector,
    )

    fun readFromFiles(files: Set<Path>)
}
