package ch.cassiamon.pluginapi.registration

import ch.cassiamon.pluginapi.filesystem.FileSystemAccess
import ch.cassiamon.pluginapi.logger.LoggerFacade
import ch.cassiamon.pluginapi.schema.SchemaAccess

interface InputSourceRegistration {

    fun receiveDataCollector(): InputSourceDataCollector

    fun receiveSchema(): SchemaAccess

    fun receiveLoggerFacade(): LoggerFacade

    fun receiveFileSystemAccess(): FileSystemAccess

}
