package ch.cassiamon.api.registration

import ch.cassiamon.api.extensions.ExtensionName
import java.nio.file.Path

interface InputSourceExtensionAccess {

    fun dataCollectionWithFilesInputSourceExtension(extensionName: ExtensionName, inputFiles: Set<Path>)

}
