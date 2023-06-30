package ch.cassiamon.engine.domain.process

import ch.cassiamon.api.registration.*
import ch.cassiamon.api.schema.SchemaAccess
import ch.cassiamon.engine.ProcessSession
import ch.cassiamon.api.registration.ConceptData
import ch.cassiamon.engine.extension.ExtensionAccessHolder
import ch.cassiamon.engine.domain.datacollection.ConceptDataCollector
import ch.cassiamon.engine.domain.process.proxy.ProxyCreator

class DomainUnitProcessInputDataImpl<I: Any>(
    processSession: ProcessSession,
    schemaAccess: SchemaAccess,
    inputDefinitionClass: Class<I>
): DomainUnitProcessInputData<I> {

    private val conceptDataCollector: ConceptDataCollector = ConceptDataCollector(schemaAccess, validateConcept = true)
    private val dataCollectorInterface: I = ProxyCreator.createDataCollectorProxy(inputDefinitionClass, conceptDataCollector)
    private val extensionAccess: ExtensionAccessHolder = ExtensionAccessHolder(processSession.fileSystemAccess, processSession.loggerFacade, processSession.parameterAccess, conceptDataCollector)
    private val inputSourceExtensionAccess = InputSourceExtensionAccessImpl(extensionAccess)


    init {
        extensionAccess.initializeSchema(schemaAccess)
    }

    override fun getDataCollector(): I {
        return dataCollectorInterface
    }

    override fun getInputDataExtensionAccess(): InputSourceExtensionAccess {
        return inputSourceExtensionAccess
    }

    override fun provideConceptEntries(): List<ConceptData> {
        return conceptDataCollector.provideConceptData()
    }

}
