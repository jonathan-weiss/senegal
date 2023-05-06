package ch.cassiamon.engine.schema.registration

import ch.cassiamon.engine.extension.ExtensionAccess
import ch.cassiamon.engine.inputsource.ModelInputDataCollector
import ch.cassiamon.engine.schema.Schema
import ch.cassiamon.pluginapi.extensions.ExtensionName
import ch.cassiamon.pluginapi.filesystem.FileSystemAccess
import ch.cassiamon.pluginapi.logger.LoggerFacade
import ch.cassiamon.pluginapi.parameter.ParameterAccess
import ch.cassiamon.pluginapi.registration.InputSourceDataCollector
import ch.cassiamon.pluginapi.registration.InputSourceRegistration
import ch.cassiamon.pluginapi.schema.SchemaAccess
import java.nio.file.Path

class InputSourceRegistrationDefaultImpl(
    private val extensionAccess: ExtensionAccess,
    private val modelInputDataCollector: ModelInputDataCollector,
    private val schemaProvider: SchemaProvider,
    private val loggerFacade: LoggerFacade,
    private val fileSystemAccess: FileSystemAccess,
    private val parameterAccess: ParameterAccess,
    ) : InputSourceRegistration {
    override fun receiveDataCollector(): InputSourceDataCollector {
        return modelInputDataCollector
    }

    override fun dataCollectionWithFilesInputSourceExtension(extensionName: ExtensionName, inputFiles: Set<Path>) {
        extensionAccess.getFilesInputSourceExtension(extensionName).readFromFiles(inputFiles)
    }

    override fun receiveSchema(): SchemaAccess {
        return schemaProvider.provideSchema()
    }

    override fun receiveLoggerFacade(): LoggerFacade {
        return loggerFacade
    }

    override fun receiveFileSystemAccess(): FileSystemAccess {
        return fileSystemAccess
    }

    override fun receiveParameterAccess(): ParameterAccess {
        return parameterAccess
    }
}
