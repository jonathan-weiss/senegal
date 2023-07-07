package ch.cassiamon.api.datacollection.extensions

import ch.cassiamon.api.extensions.ExtensionName
import java.nio.file.Path

interface DataCollectionExtensionAccess {

    fun collectWithDataCollectionFromFilesExtension(extensionName: ExtensionName, inputFiles: Set<Path>)

}
