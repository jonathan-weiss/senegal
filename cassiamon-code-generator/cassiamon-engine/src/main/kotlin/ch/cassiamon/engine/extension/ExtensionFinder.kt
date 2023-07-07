package ch.cassiamon.engine.extension

import ch.cassiamon.api.process.datacollection.extensions.DataCollectionFromFilesExtension
import java.util.*

object ExtensionFinder {

    fun findAllDataCollectionFromFilesExtensions(): List<DataCollectionFromFilesExtension> {
        val extensionServiceLoader: ServiceLoader<DataCollectionFromFilesExtension> = ServiceLoader.load(
            DataCollectionFromFilesExtension::class.java)

        return extensionServiceLoader.toList()
    }

}
