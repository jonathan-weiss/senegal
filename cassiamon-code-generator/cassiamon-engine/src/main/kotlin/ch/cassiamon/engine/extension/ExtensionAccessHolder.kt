package ch.cassiamon.engine.extension

import ch.cassiamon.engine.inputsource.ModelInputDataCollector
import ch.cassiamon.pluginapi.extensions.ExtensionInitialization
import ch.cassiamon.pluginapi.extensions.ExtensionName
import ch.cassiamon.pluginapi.extensions.inputsource.InputSourceExtensionInitialization
import ch.cassiamon.pluginapi.extensions.inputsource.files.FilesInputSourceExtension
import ch.cassiamon.pluginapi.extensions.template.ClasspathTemplateExtension
import ch.cassiamon.pluginapi.filesystem.FileSystemAccess
import ch.cassiamon.pluginapi.logger.LoggerFacade
import ch.cassiamon.pluginapi.parameter.ParameterAccess
import ch.cassiamon.pluginapi.schema.SchemaAccess

class ExtensionAccessHolder(
    private val fileSystemAccess: FileSystemAccess,
    private val loggerFacade: LoggerFacade,
    private val parameterAccess: ParameterAccess,
    private val modelInputDataCollector: ModelInputDataCollector,
): ExtensionAccess {

    private val classpathTemplateExtensions: Map<ExtensionName, ClasspathTemplateExtension> =
        ExtensionFinder.findAllClasspathTemplateExtensions()
            .onEach { initializeExtension(it) }
            .associateBy { it.getExtensionName() }

    private val filesInputSourceExtensions: Map<ExtensionName, FilesInputSourceExtension> =
        ExtensionFinder.findAllFilesInputSourceExtensions()
            .onEach { initializeExtension(it) }
            .onEach { initializeInputSourceExtension(it) }
            .associateBy { it.getExtensionName() }


    private fun initializeExtension(extension: ExtensionInitialization) {
        extension.initializeExtension(
            loggerFacade = loggerFacade,
            parameterAccess = parameterAccess,
        )
    }

    private fun initializeInputSourceExtension(extension: InputSourceExtensionInitialization) {
        extension.initializeInputSourceExtension(
            inputSourceDataCollector = modelInputDataCollector,
            fileSystemAccess = fileSystemAccess,
        )
    }

    fun initializeSchema(schemaAccess: SchemaAccess) {
        filesInputSourceExtensions.values.forEach { it.initializeSchema(schemaAccess) }
    }
    override fun getClasspathTemplateExtension(extensionName: ExtensionName): ClasspathTemplateExtension {
        return classpathTemplateExtensions[extensionName] ?: throwExtensionNotFound(extensionName)
    }

    override fun getFilesInputSourceExtension(extensionName: ExtensionName): FilesInputSourceExtension {
        return filesInputSourceExtensions[extensionName] ?: throwExtensionNotFound(extensionName)
    }

    private fun throwExtensionNotFound(extensionName: ExtensionName): Nothing {
        throw IllegalArgumentException("No extension for extension name ${extensionName.name}")
    }
}
