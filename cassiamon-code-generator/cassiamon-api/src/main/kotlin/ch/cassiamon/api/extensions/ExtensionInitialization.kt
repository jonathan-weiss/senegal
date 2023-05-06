package ch.cassiamon.api.extensions

import ch.cassiamon.api.logger.LoggerFacade
import ch.cassiamon.api.parameter.ParameterAccess

interface ExtensionInitialization {

    fun initializeExtension(loggerFacade: LoggerFacade, parameterAccess: ParameterAccess)
}
