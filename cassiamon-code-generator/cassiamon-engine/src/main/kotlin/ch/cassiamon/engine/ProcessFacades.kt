package ch.cassiamon.engine

import ch.cassiamon.engine.filesystem.PhysicalFilesFileSystemAccess
import ch.cassiamon.engine.logger.JavaUtilLoggerFacade
import ch.cassiamon.engine.parameters.*

class ProcessFacades {

    val fileSystemAccess = PhysicalFilesFileSystemAccess()
    val loggerFacade = JavaUtilLoggerFacade(fileSystemAccess)

    private val parameterSources: List<ParameterSource> = listOf(
        EnvironmentVariablesParameterSource,
        SystemPropertyParameterSource,
        DefaultPropertyFileParameterSource(fileSystemAccess),
    )

    val parameterAccess = MultipleSourcesParameterAccess(parameterSources)

}
