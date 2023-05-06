package ch.cassiamon.pluginapi.registration

import ch.cassiamon.pluginapi.extensions.ClasspathLocation
import ch.cassiamon.pluginapi.extensions.ExtensionName
import ch.cassiamon.pluginapi.filesystem.FileSystemAccess
import ch.cassiamon.pluginapi.logger.LoggerFacade
import ch.cassiamon.pluginapi.parameter.ParameterAccess
import ch.cassiamon.pluginapi.schema.SchemaAccess
import ch.cassiamon.pluginapi.template.TargetGeneratedFileWithModel
import ch.cassiamon.pluginapi.template.TemplateRenderer
import java.nio.file.Path

interface InputSourceRegistration {

    fun receiveDataCollector(): InputSourceDataCollector

    fun dataCollectionWithFilesInputSourceExtension(
        extensionName: ExtensionName,
        inputFiles: Set<Path>
    )

    fun receiveSchema(): SchemaAccess

    fun receiveLoggerFacade(): LoggerFacade

    fun receiveFileSystemAccess(): FileSystemAccess
    fun receiveParameterAccess(): ParameterAccess

}
