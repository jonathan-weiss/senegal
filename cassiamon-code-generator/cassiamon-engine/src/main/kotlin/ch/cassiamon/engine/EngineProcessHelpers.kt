package ch.cassiamon.engine

import ch.cassiamon.engine.extension.ExtensionAccess
import ch.cassiamon.engine.extension.ExtensionAccessHolder
import ch.cassiamon.engine.filesystem.PhysicalFilesFileSystemAccess
import ch.cassiamon.engine.inputsource.ModelInputDataCollector
import ch.cassiamon.engine.logger.JavaUtilLoggerFacade
import ch.cassiamon.engine.parameters.*
import ch.cassiamon.pluginapi.filesystem.FileSystemAccess
import ch.cassiamon.pluginapi.logger.LoggerFacade
import ch.cassiamon.pluginapi.parameter.ParameterAccess
import ch.cassiamon.pluginapi.registration.Registrar

class EngineProcessHelpers(
    val registrars: List<Registrar> = emptyList(),
    val fileSystemAccess: FileSystemAccess = PhysicalFilesFileSystemAccess(),
    val loggerFacade: LoggerFacade = JavaUtilLoggerFacade(fileSystemAccess),
    private val parameterSources: List<ParameterSource> = listOf(
        EnvironmentVariablesParameterSource,
        SystemPropertyParameterSource,
        DefaultPropertyFileParameterSource(fileSystemAccess),
    ),
    val parameterAccess: ParameterAccess = MultipleSourcesParameterAccess(parameterSources),
    val modelInputDataCollector: ModelInputDataCollector = ModelInputDataCollector(),
    val extensionAccess: ExtensionAccess = ExtensionAccessHolder(fileSystemAccess, loggerFacade, parameterAccess, modelInputDataCollector)
)
