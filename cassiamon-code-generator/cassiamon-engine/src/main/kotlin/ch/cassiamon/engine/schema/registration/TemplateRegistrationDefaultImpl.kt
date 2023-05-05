package ch.cassiamon.engine.schema.registration

import ch.cassiamon.pluginapi.filesystem.FileSystemAccess
import ch.cassiamon.pluginapi.logger.LoggerFacade
import ch.cassiamon.pluginapi.parameter.ParameterAccess
import ch.cassiamon.pluginapi.registration.*
import ch.cassiamon.pluginapi.schema.SchemaAccess

class TemplateRegistrationDefaultImpl(
    private val schemaProvider: SchemaProvider,
    private val loggerFacade: LoggerFacade,
    private val fileSystemAccess: FileSystemAccess,
    private val parameterAccess: ParameterAccess,
    ): TemplatesRegistration, TemplateProvider {
    private val templateFunctions: MutableList<TemplateFunction> = mutableListOf()

    override fun newTemplate(templateFunction: TemplateFunction) {
        templateFunctions.add(templateFunction)
    }

    override fun provideTemplates(): List<TemplateFunction> {
        return templateFunctions
    }

    override fun receiveSchema(): SchemaAccess {
        return schemaProvider.provideSchema()
    }

    override fun receiveLoggerFacade(): LoggerFacade {
        return loggerFacade
    }

    override fun receiveFileSystemAccess(): FileSystemAccess {
        return fileSystemAccess
    }

    override fun receiveParameterAccess(): ParameterAccess {
        return parameterAccess
    }

}
