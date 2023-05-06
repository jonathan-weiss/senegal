package ch.cassiamon.pluginapi.extensions

import ch.cassiamon.pluginapi.logger.LoggerFacade
import ch.cassiamon.pluginapi.parameter.ParameterAccess

interface ExtensionInitialization {

    fun initializeExtension(loggerFacade: LoggerFacade, parameterAccess: ParameterAccess)
}
