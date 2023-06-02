package ch.cassiamon.engine

import ch.cassiamon.engine.extension.ExtensionAccessHolder
import ch.cassiamon.engine.filesystem.PhysicalFilesFileSystemAccess
import ch.cassiamon.engine.inputsource.ModelInputDataCollector
import ch.cassiamon.engine.logger.JavaUtilLoggerFacade
import ch.cassiamon.engine.parameters.*
import ch.cassiamon.api.filesystem.FileSystemAccess
import ch.cassiamon.api.logger.LoggerFacade
import ch.cassiamon.api.parameter.ParameterAccess
import ch.cassiamon.api.registration.DomainUnit

class ProcessSession(
    val domainUnits: List<DomainUnit> = emptyList(),
    val fileSystemAccess: FileSystemAccess = PhysicalFilesFileSystemAccess(),
    val loggerFacade: LoggerFacade = JavaUtilLoggerFacade(fileSystemAccess),
    private val parameterSources: List<ParameterSource> = listOf(
        DefaultPropertyFileParameterSource(fileSystemAccess),
        EnvironmentVariablesParameterSource,
        SystemPropertyParameterSource,
    ),
    val parameterAccess: ParameterAccess = MultipleSourcesParameterAccess(parameterSources),
    val modelInputDataCollector: ModelInputDataCollector = ModelInputDataCollector(),
    val extensionAccess: ExtensionAccessHolder = ExtensionAccessHolder(fileSystemAccess, loggerFacade, parameterAccess, modelInputDataCollector)
)
