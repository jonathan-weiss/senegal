package ch.cassiamon.engine.schema.registration

import ch.cassiamon.engine.EngineProcessHelpers
import ch.cassiamon.engine.inputsource.InputSourceDataProvider
import ch.cassiamon.engine.inputsource.ModelInputData
import ch.cassiamon.engine.inputsource.ModelInputDataCollector
import ch.cassiamon.engine.schema.Schema
import ch.cassiamon.pluginapi.registration.*

class RegistrationApiDefaultImpl(engineProcessHelpers: EngineProcessHelpers): RegistrationApi, SchemaProvider, TemplateProvider, InputSourceDataProvider {
    private val modelInputDataCollector = ModelInputDataCollector()
    private val schemaRegistrationImpl = SchemaRegistrationDefaultImpl()
    private val templateRegistrationImpl = TemplateRegistrationDefaultImpl()
    private val inputSourceRegistrationImpl = InputSourceRegistrationDefaultImpl(
        modelInputDataCollector= modelInputDataCollector,
        schemaProvider = schemaRegistrationImpl,
        loggerFacade = engineProcessHelpers.loggerFacade,
        fileSystemAccess = engineProcessHelpers.fileSystemAccess,
        parameterAccess = engineProcessHelpers.parameterAccess,
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
