package ch.cassiamon.engine.domain.process

import ch.cassiamon.api.registration.*
import ch.cassiamon.api.schema.SchemaAccess
import ch.cassiamon.engine.ProcessSession
import ch.cassiamon.engine.domain.Schema
import ch.cassiamon.engine.domain.SchemaCreator
import ch.cassiamon.engine.domain.registration.SchemaProvider
import ch.cassiamon.engine.domain.registration.TemplateProvider
import ch.cassiamon.engine.extension.ExtensionAccessHolder
import ch.cassiamon.engine.inputsource.InputSourceDataProvider
import ch.cassiamon.engine.inputsource.ModelInputData
import ch.cassiamon.engine.inputsource.ModelInputDataCollector
import java.lang.reflect.Proxy

class DomainUnitProcessInputDataImpl<S: Any>(processSession: ProcessSession, private val schemaDefinitionClass: Class<S>):
    DomainUnitProcessInputData, SchemaProvider,  InputSourceDataProvider {

    private val modelInputDataCollector: ModelInputDataCollector = ModelInputDataCollector()
    private val extensionAccess: ExtensionAccessHolder = ExtensionAccessHolder(processSession.fileSystemAccess, processSession.loggerFacade, processSession.parameterAccess, modelInputDataCollector)
    private val inputSourceExtensionAccess = InputSourceExtensionAccessImpl(extensionAccess)


    init {
        extensionAccess.initializeSchema(provideSchema())
    }

    override fun getDataCollector(): InputSourceDataCollector {
        return modelInputDataCollector
    }

    override fun getInputDataExtensionAccess(): InputSourceExtensionAccess {
        return inputSourceExtensionAccess
    }

    override fun provideSchema(): SchemaAccess {
        return SchemaCreator.createSchemaFromSchemaDefinitionClass(schemaDefinitionClass)
    }

    override fun provideModelInputData(): ModelInputData {
        return modelInputDataCollector.provideModelInputData()
    }
}
