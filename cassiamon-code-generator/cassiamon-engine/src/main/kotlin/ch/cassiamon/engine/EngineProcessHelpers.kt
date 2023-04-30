package ch.cassiamon.engine

import ch.cassiamon.engine.filesystem.PhysicalFilesFileSystemAccess
import ch.cassiamon.engine.logger.JavaUtilLoggerFacade
import ch.cassiamon.engine.parameters.*
import ch.cassiamon.pluginapi.filesystem.FileSystemAccess
import ch.cassiamon.pluginapi.logger.LoggerFacade
import ch.cassiamon.pluginapi.parameter.ParameterAccess

class EngineProcessHelpers(
    val fileSystemAccess: FileSystemAccess = PhysicalFilesFileSystemAccess(),
    val loggerFacade: LoggerFacade = JavaUtilLoggerFacade(fileSystemAccess),
    private val parameterSources: List<ParameterSource> = listOf(
        EnvironmentVariablesParameterSource,
        SystemPropertyParameterSource,
        DefaultPropertyFileParameterSource(fileSystemAccess),
    ),
    val parameterAccess: ParameterAccess = MultipleSourcesParameterAccess(parameterSources),
)
