package ch.cassiamon.engine.extension

import ch.cassiamon.api.extensions.ExtensionInitialization
import ch.cassiamon.api.extensions.ExtensionName
import ch.cassiamon.api.extensions.inputsource.ConceptAndFacetDataCollector
import ch.cassiamon.api.extensions.inputsource.InputSourceExtensionInitialization
import ch.cassiamon.api.extensions.inputsource.files.FilesInputSourceExtension
import ch.cassiamon.api.extensions.template.ClasspathTemplateExtension
import ch.cassiamon.api.filesystem.FileSystemAccess
import ch.cassiamon.api.logger.LoggerFacade
import ch.cassiamon.api.parameter.ParameterAccess
import ch.cassiamon.api.schema.SchemaAccess

class ExtensionAccessHolder(
    private val fileSystemAccess: FileSystemAccess,
    private val loggerFacade: LoggerFacade,
    private val parameterAccess: ParameterAccess,
    private val conceptAndFacetDataCollector: ConceptAndFacetDataCollector,
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
            conceptAndFacetDataCollector = conceptAndFacetDataCollector,
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
