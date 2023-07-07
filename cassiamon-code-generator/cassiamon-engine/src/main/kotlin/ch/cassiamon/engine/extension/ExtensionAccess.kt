package ch.cassiamon.engine.extension

import ch.cassiamon.api.extensions.ExtensionName
import ch.cassiamon.api.datacollection.extensions.DataCollectionFromFilesExtension

interface ExtensionAccess {

    fun getDataCollectionFromFilesExtension(extensionName: ExtensionName): DataCollectionFromFilesExtension
}
