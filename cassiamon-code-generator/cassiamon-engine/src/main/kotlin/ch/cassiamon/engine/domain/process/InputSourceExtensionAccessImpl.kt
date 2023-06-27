package ch.cassiamon.engine.domain.process

import ch.cassiamon.api.extensions.ExtensionName
import ch.cassiamon.api.registration.InputSourceExtensionAccess
import ch.cassiamon.engine.extension.ExtensionAccessHolder
import java.nio.file.Path

class InputSourceExtensionAccessImpl(private val extensionAccess: ExtensionAccessHolder): InputSourceExtensionAccess {
    override fun dataCollectionWithFilesInputSourceExtension(extensionName: ExtensionName, inputFiles: Set<Path>) {
        // TODO think about refactor this and inject data collector directly
        extensionAccess.getFilesInputSourceExtension(extensionName).readFromFiles(inputFiles)
    }
}
