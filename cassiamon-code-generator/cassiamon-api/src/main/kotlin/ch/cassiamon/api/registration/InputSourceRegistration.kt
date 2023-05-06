package ch.cassiamon.api.registration

import ch.cassiamon.api.extensions.ExtensionName
import ch.cassiamon.api.filesystem.FileSystemAccess
import ch.cassiamon.api.logger.LoggerFacade
import ch.cassiamon.api.parameter.ParameterAccess
import ch.cassiamon.api.schema.SchemaAccess
import java.nio.file.Path

interface InputSourceRegistration {

    fun receiveDataCollector(): InputSourceDataCollector

    fun dataCollectionWithFilesInputSourceExtension(
        extensionName: ExtensionName,
        inputFiles: Set<Path>
    )

    fun receiveSchema(): SchemaAccess

    fun receiveLoggerFacade(): LoggerFacade

    fun receiveFileSystemAccess(): FileSystemAccess
    fun receiveParameterAccess(): ParameterAccess

}
