package ch.cassiamon.api.process.datacollection.extensions

import ch.cassiamon.api.extensions.Extension
import ch.cassiamon.api.filesystem.FileSystemAccess
import ch.cassiamon.api.logger.LoggerFacade
import ch.cassiamon.api.parameter.ParameterAccess
import ch.cassiamon.api.process.schema.SchemaAccess
import java.nio.file.Path

interface DataCollectionFromFilesExtension: Extension {

    fun initializeDataCollectionExtension(
        loggerFacade: LoggerFacade,
        parameterAccess: ParameterAccess,
        fileSystemAccess: FileSystemAccess,
        schemaAccess: SchemaAccess,
        extensionDataCollector: ExtensionDataCollector,
    )

    fun readFromFiles(files: Set<Path>)
}
