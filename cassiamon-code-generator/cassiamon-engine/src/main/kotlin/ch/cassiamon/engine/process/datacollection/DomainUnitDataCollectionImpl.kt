package ch.cassiamon.engine.process.datacollection

import ch.cassiamon.api.process.datacollection.DomainUnitDataCollection
import ch.cassiamon.api.process.schema.SchemaAccess
import ch.cassiamon.engine.process.ProcessSession
import ch.cassiamon.api.process.datacollection.ConceptData
import ch.cassiamon.api.process.datacollection.extensions.DataCollectionExtensionAccess
import ch.cassiamon.engine.process.datacollection.proxy.DataCollectorInvocationHandler
import ch.cassiamon.engine.extension.ExtensionHolder
import ch.cassiamon.engine.proxy.ProxyCreator

class DomainUnitDataCollectionImpl<I: Any>(
    processSession: ProcessSession,
    schemaAccess: SchemaAccess,
    inputDefinitionClass: Class<I>
): DomainUnitDataCollection<I> {

    private val conceptDataCollector: ConceptDataCollector = ConceptDataCollector(schemaAccess, validateConcept = true)
    private val dataCollectorInterface: I = ProxyCreator.createProxy(inputDefinitionClass, DataCollectorInvocationHandler(conceptDataCollector))
    private val extensionAccess: ExtensionHolder = ExtensionHolder(processSession.fileSystemAccess, processSession.loggerFacade, processSession.parameterAccess, schemaAccess, conceptDataCollector)

    override fun getDataCollector(): I {
        return dataCollectorInterface
    }

    override fun getDataCollectionExtensionAccess(): DataCollectionExtensionAccess {
        return extensionAccess
    }

    override fun getCollectedData(): List<ConceptData> {
        return conceptDataCollector.provideConceptData()
    }

}
