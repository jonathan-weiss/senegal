package ch.cassiamon.engine.schema.registration

import ch.cassiamon.engine.filesystem.PhysicalFilesFileSystemAccess
import ch.cassiamon.engine.inputsource.InputSourceDataProvider
import ch.cassiamon.engine.inputsource.ModelInputData
import ch.cassiamon.engine.inputsource.ModelInputDataCollector
import ch.cassiamon.engine.logger.JavaUtilLoggerFacade
import ch.cassiamon.engine.schema.Schema
import ch.cassiamon.pluginapi.registration.*

class RegistrationApiDefaultImpl: RegistrationApi, SchemaProvider, TemplateProvider, InputSourceDataProvider {
    private val fileSystemAccess = PhysicalFilesFileSystemAccess() // TODO probably not the right place to initialize
    private val loggerFacade = JavaUtilLoggerFacade(fileSystemAccess) // TODO probably not the right place to initialize


    private val modelInputDataCollector = ModelInputDataCollector()
    private val schemaRegistrationImpl = SchemaRegistrationDefaultImpl()
    private val templateRegistrationImpl = TemplateRegistrationDefaultImpl()
    private val inputSourceRegistrationImpl = InputSourceRegistrationDefaultImpl(
        modelInputDataCollector= modelInputDataCollector,
        schemaProvider = schemaRegistrationImpl,
        loggerFacade = loggerFacade,
        fileSystemAccess = fileSystemAccess
        )



    override fun configureSchema(schemaRegistration: SchemaRegistration.() -> Unit) {
        schemaRegistration(schemaRegistrationImpl)
    }

    override fun configureTemplates(templateRegistration: TemplatesRegistration.() -> Unit) {
        templateRegistration(templateRegistrationImpl)
    }

    override fun configureDataCollector(inputSourceRegistration: InputSourceRegistration.() -> Unit) {
        inputSourceRegistration(inputSourceRegistrationImpl)
    }

    override fun provideModelInputData(): ModelInputData {
        return modelInputDataCollector.provideModelInputData()
    }

    override fun provideSchema(): Schema {
        return schemaRegistrationImpl.provideSchema()
    }

    override fun provideTemplates(): List<TemplateFunction> {
        return templateRegistrationImpl.provideTemplates()
    }
}
