package ch.cassiamon.engine.domain.registration

import ch.cassiamon.engine.extension.ExtensionAccess
import ch.cassiamon.engine.inputsource.ModelInputDataCollector
import ch.cassiamon.api.extensions.ExtensionName
import ch.cassiamon.api.filesystem.FileSystemAccess
import ch.cassiamon.api.logger.LoggerFacade
import ch.cassiamon.api.parameter.ParameterAccess
import ch.cassiamon.api.registration.InputSourceDataCollector
import ch.cassiamon.api.registration.InputSourceRegistration
import ch.cassiamon.api.schema.SchemaAccess
import java.nio.file.Path

class InputSourceRegistrationDefaultImpl(
    private val extensionAccess: ExtensionAccess,
    private val modelInputDataCollector: ModelInputDataCollector,
    ) : InputSourceRegistration {
    override fun receiveDataCollector(): InputSourceDataCollector {
        return modelInputDataCollector
    }

    override fun dataCollectionWithFilesInputSourceExtension(extensionName: ExtensionName, inputFiles: Set<Path>) {
        extensionAccess.getFilesInputSourceExtension(extensionName).readFromFiles(inputFiles)
    }
}
