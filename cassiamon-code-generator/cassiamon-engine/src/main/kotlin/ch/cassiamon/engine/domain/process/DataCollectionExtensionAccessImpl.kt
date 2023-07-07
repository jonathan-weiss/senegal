package ch.cassiamon.engine.domain.process

import ch.cassiamon.api.extensions.ExtensionName
import ch.cassiamon.api.datacollection.extensions.DataCollectionExtensionAccess
import ch.cassiamon.engine.extension.ExtensionAccessHolder
import java.nio.file.Path

class DataCollectionExtensionAccessImpl(private val extensionAccess: ExtensionAccessHolder):
    DataCollectionExtensionAccess {
    override fun collectWithDataCollectionFromFilesExtension(extensionName: ExtensionName, inputFiles: Set<Path>) {
        // TODO think about refactor this and inject data collector directly
        extensionAccess.getDataCollectionFromFilesExtension(extensionName).readFromFiles(inputFiles)
    }
}
