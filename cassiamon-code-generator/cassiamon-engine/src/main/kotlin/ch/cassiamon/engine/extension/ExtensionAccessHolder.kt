package ch.cassiamon.engine.extension

import ch.cassiamon.api.extensions.ExtensionName
import ch.cassiamon.api.datacollection.extensions.ConceptAndFacetDataCollector
import ch.cassiamon.api.datacollection.extensions.DataCollectionFromFilesExtension
import ch.cassiamon.api.filesystem.FileSystemAccess
import ch.cassiamon.api.logger.LoggerFacade
import ch.cassiamon.api.parameter.ParameterAccess
import ch.cassiamon.api.schema.SchemaAccess

class ExtensionAccessHolder(
    private val fileSystemAccess: FileSystemAccess,
    private val loggerFacade: LoggerFacade,
    private val parameterAccess: ParameterAccess,
    private val schemaAccess: SchemaAccess,
    private val conceptAndFacetDataCollector: ConceptAndFacetDataCollector,
): ExtensionAccess {

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

    override fun getDataCollectionFromFilesExtension(extensionName: ExtensionName): DataCollectionFromFilesExtension {
        return dataCollectionFromFilesExtensions[extensionName] ?: throwExtensionNotFound(extensionName)
    }

    private fun throwExtensionNotFound(extensionName: ExtensionName): Nothing {
        throw IllegalArgumentException("No extension for extension name ${extensionName.name}")
    }
}
