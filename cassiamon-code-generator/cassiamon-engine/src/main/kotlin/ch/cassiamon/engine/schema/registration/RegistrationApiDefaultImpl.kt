package ch.cassiamon.engine.schema.registration

import ch.cassiamon.engine.inputsource.InputSourceDataProvider
import ch.cassiamon.engine.inputsource.ModelInputData
import ch.cassiamon.engine.inputsource.ModelInputDataCollector
import ch.cassiamon.engine.schema.Schema
import ch.cassiamon.pluginapi.registration.*

class RegistrationApiDefaultImpl: RegistrationApi, SchemaProvider, TemplateProvider, InputSourceDataProvider {
    private val schemaRegistrationImpl = SchemaRegistrationDefaultImpl()
    private val templateRegistrationImpl = TemplateRegistrationDefaultImpl()
    private val modelInputDataCollector = ModelInputDataCollector()



    override fun configureSchema(schemaRegistration: SchemaRegistration.() -> Unit) {
        schemaRegistration(schemaRegistrationImpl)
    }

    override fun configureTemplates(templateRegistration: TemplatesRegistration.() -> Unit) {
        templateRegistration(templateRegistrationImpl)
    }

    override fun provideModelInputData(): ModelInputData {
        return modelInputDataCollector.provideModelInputData()
    }

    override fun collectData(inputSourceRegistration: (dataCollector: InputSourceDataCollector) -> Unit) {
        inputSourceRegistration(modelInputDataCollector)
    }

    override fun provideSchema(): Schema {
        return schemaRegistrationImpl.provideSchema()
    }

    override fun provideTemplates(): List<TemplateFunction> {
        return templateRegistrationImpl.provideTemplates()
    }
}
