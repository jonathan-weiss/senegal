package ch.cassiamon.api.registration

import ch.cassiamon.api.extensions.ExtensionName
import java.nio.file.Path

typealias InputSourceRegistrationApi = (InputSourceRegistration.() -> Unit) -> Unit
interface InputSourceRegistration {

    fun receiveDataCollector(): InputSourceDataCollector

    fun dataCollectionWithFilesInputSourceExtension(
        extensionName: ExtensionName,
        inputFiles: Set<Path>
    )
}
