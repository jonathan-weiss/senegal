package ch.cassiamon.engine.process.templating

import ch.cassiamon.api.process.templating.DomainUnitProcessTargetFilesData
import ch.cassiamon.api.process.templating.TargetFilesCollector
import ch.cassiamon.engine.process.conceptgraph.ConceptGraph
import ch.cassiamon.engine.proxy.ProxyCreator
import ch.cassiamon.engine.process.schema.proxy.SchemaInstanceInvocationHandler

class DomainUnitProcessTargetFilesDataImpl<S: Any>(
    schemaDefinitionClass: Class<S>,
    conceptGraph: ConceptGraph
): DomainUnitProcessTargetFilesData<S> {


    private val targetFilesCollector: TargetFilesCollector = ListTargetFilesCollectorImpl()
    private val schemaInstance = ProxyCreator.createProxy(schemaDefinitionClass, SchemaInstanceInvocationHandler(conceptGraph))

    override fun getSchemaInstance(): S {
        return schemaInstance
    }


    override fun getTargetFilesCollector(): TargetFilesCollector {
        return targetFilesCollector
    }
}
