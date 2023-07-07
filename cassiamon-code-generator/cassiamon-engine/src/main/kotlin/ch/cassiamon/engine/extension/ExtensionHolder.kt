package ch.cassiamon.engine.extension

import ch.cassiamon.api.extensions.ExtensionName
import ch.cassiamon.api.process.datacollection.extensions.ConceptAndFacetDataCollector
import ch.cassiamon.api.process.datacollection.extensions.DataCollectionFromFilesExtension
import ch.cassiamon.api.filesystem.FileSystemAccess
import ch.cassiamon.api.logger.LoggerFacade
import ch.cassiamon.api.parameter.ParameterAccess
import ch.cassiamon.api.process.datacollection.extensions.DataCollectionExtensionAccess
import ch.cassiamon.api.process.schema.SchemaAccess
import java.nio.file.Path

class ExtensionHolder(
    private val fileSystemAccess: FileSystemAccess,
    private val loggerFacade: LoggerFacade,
    private val parameterAccess: ParameterAccess,
    private val schemaAccess: SchemaAccess,
    private val conceptAndFacetDataCollector: ConceptAndFacetDataCollector,
): DataCollectionExtensionAccess {

    private val dataCollectionFromFilesExtensions: Map<ExtensionName, DataCollectionFromFilesExtension> =
        ExtensionFinder.findAllDataCollectionFromFilesExtensions()
            .onEach { initializeDataCollectionExtension(it) }
            .associateBy { it.getExtensionName() }


    private fun initializeDataCollectionExtension(extension: DataCollectionFromFilesExtension) {
        extension.initializeDataCollectionExtension(
            loggerFacade = loggerFacade,
            parameterAccess = parameterAccess,
            conceptAndFacetDataCollector = conceptAndFacetDataCollector,
            fileSystemAccess = fileSystemAccess,
            schemaAccess = schemaAccess,
        )
    }

    override fun collectWithDataCollectionFromFilesExtension(extensionName: ExtensionName, inputFiles: Set<Path>) {
        dataCollectionFromFilesExtensions[extensionName]?.readFromFiles(inputFiles) ?: throwExtensionNotFound(extensionName)
    }

    private fun throwExtensionNotFound(extensionName: ExtensionName): Nothing {
        throw IllegalArgumentException("No extension for extension name ${extensionName.name}")
    }
}
