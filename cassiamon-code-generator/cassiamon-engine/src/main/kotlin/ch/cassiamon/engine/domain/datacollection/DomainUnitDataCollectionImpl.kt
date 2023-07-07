package ch.cassiamon.engine.domain.datacollection

import ch.cassiamon.api.datacollection.DomainUnitDataCollection
import ch.cassiamon.api.schema.SchemaAccess
import ch.cassiamon.engine.ProcessSession
import ch.cassiamon.api.datacollection.ConceptData
import ch.cassiamon.api.datacollection.extensions.DataCollectionExtensionAccess
import ch.cassiamon.engine.domain.datacollection.proxy.DataCollectorInvocationHandler
import ch.cassiamon.engine.extension.ExtensionAccessHolder
import ch.cassiamon.engine.domain.process.DataCollectionExtensionAccessImpl
import ch.cassiamon.engine.proxy.ProxyCreator

class DomainUnitDataCollectionImpl<I: Any>(
    processSession: ProcessSession,
    schemaAccess: SchemaAccess,
    inputDefinitionClass: Class<I>
): DomainUnitDataCollection<I> {

    private val conceptDataCollector: ConceptDataCollector = ConceptDataCollector(schemaAccess, validateConcept = true)
    private val dataCollectorInterface: I = ProxyCreator.createProxy(inputDefinitionClass, DataCollectorInvocationHandler(conceptDataCollector))
    private val extensionAccess: ExtensionAccessHolder = ExtensionAccessHolder(processSession.fileSystemAccess, processSession.loggerFacade, processSession.parameterAccess, schemaAccess, conceptDataCollector)
    private val inputSourceExtensionAccess = DataCollectionExtensionAccessImpl(extensionAccess)

    override fun getDataCollector(): I {
        return dataCollectorInterface
    }

    override fun getInputDataExtensionAccess(): DataCollectionExtensionAccess {
        return inputSourceExtensionAccess
    }

    override fun provideConceptEntries(): List<ConceptData> {
        return conceptDataCollector.provideConceptData()
    }

}
