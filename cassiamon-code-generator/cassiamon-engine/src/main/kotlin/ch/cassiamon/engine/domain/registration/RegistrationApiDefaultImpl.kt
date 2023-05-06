package ch.cassiamon.engine.domain.registration

import ch.cassiamon.engine.ProcessSession
import ch.cassiamon.engine.inputsource.InputSourceDataProvider
import ch.cassiamon.engine.inputsource.ModelInputData
import ch.cassiamon.engine.inputsource.ModelInputDataCollector
import ch.cassiamon.engine.domain.Schema
import ch.cassiamon.api.registration.*

class RegistrationApiDefaultImpl(processSession: ProcessSession): SchemaProvider, TemplateProvider, InputSourceDataProvider {
    private val schemaRegistrationImpl = SchemaRegistrationDefaultImpl()
    private val templateRegistrationImpl = TemplateRegistrationDefaultImpl(
        extensionAccess = processSession.extensionAccess,
    )
    private val modelInputDataCollector: ModelInputDataCollector = processSession.modelInputDataCollector

    private val inputSourceRegistrationImpl = InputSourceRegistrationDefaultImpl(
        modelInputDataCollector= modelInputDataCollector,
        extensionAccess = processSession.extensionAccess
    )

    fun configureSchema(schemaRegistration: SchemaRegistration.() -> Unit) {
        schemaRegistration(schemaRegistrationImpl)
    }

    fun configureTemplates(templateRegistration: TemplatesRegistration.() -> Unit) {
        templateRegistration(templateRegistrationImpl)
    }

    fun configureDataCollector(inputSourceRegistration: InputSourceRegistration.() -> Unit) {
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
