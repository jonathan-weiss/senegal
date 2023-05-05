package ch.cassiamon.pluginapi.registration

import ch.cassiamon.pluginapi.filesystem.FileSystemAccess
import ch.cassiamon.pluginapi.logger.LoggerFacade
import ch.cassiamon.pluginapi.parameter.ParameterAccess
import ch.cassiamon.pluginapi.schema.SchemaAccess


interface TemplatesRegistration {

    fun newTemplate(templateFunction: TemplateFunction)

    fun receiveSchema(): SchemaAccess

    fun receiveLoggerFacade(): LoggerFacade

    fun receiveFileSystemAccess(): FileSystemAccess
    fun receiveParameterAccess(): ParameterAccess


}
