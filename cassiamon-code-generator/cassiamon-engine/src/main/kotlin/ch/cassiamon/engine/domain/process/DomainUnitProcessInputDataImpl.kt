package ch.cassiamon.engine.domain.process

import ch.cassiamon.api.registration.*
import ch.cassiamon.api.schema.SchemaAccess
import ch.cassiamon.engine.ProcessSession
import ch.cassiamon.engine.extension.ExtensionAccessHolder
import ch.cassiamon.api.registration.ModelInputData
import ch.cassiamon.engine.inputsource.ModelInputDataCollector

class DomainUnitProcessInputDataImpl<I: Any>(
    processSession: ProcessSession,
    schemaAccess: SchemaAccess,
    inputDefinitionClass: Class<I>
): DomainUnitProcessInputData<I> {

    private val modelInputDataCollector: ModelInputDataCollector = ModelInputDataCollector()
    private val extensionAccess: ExtensionAccessHolder = ExtensionAccessHolder(processSession.fileSystemAccess, processSession.loggerFacade, processSession.parameterAccess, modelInputDataCollector)
    private val inputSourceExtensionAccess = InputSourceExtensionAccessImpl(extensionAccess)


    init {
        extensionAccess.initializeSchema(schemaAccess)
    }

    override fun getDataCollector(): I {
        return modelInputDataCollector as I // TODO current workaround as we only use ModelInputDataCollector, later will be based on param inputDefinitionClass
    }

    override fun getInputDataExtensionAccess(): InputSourceExtensionAccess {
        return inputSourceExtensionAccess
    }

    override fun provideConceptEntries(): ConceptEntries {
        return modelInputDataCollector.provideConceptEntries()
    }

}
