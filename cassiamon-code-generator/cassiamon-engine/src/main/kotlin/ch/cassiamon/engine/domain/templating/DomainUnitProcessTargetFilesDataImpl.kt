package ch.cassiamon.engine.domain.templating

import ch.cassiamon.api.templating.DomainUnitProcessTargetFilesData
import ch.cassiamon.api.templating.TargetFilesCollector
import ch.cassiamon.engine.domain.process.conceptresolver.ResolvedConcepts
import ch.cassiamon.engine.proxy.ProxyCreator
import ch.cassiamon.engine.domain.schema.proxy.SchemaInstanceInvocationHandler

class DomainUnitProcessTargetFilesDataImpl<S: Any>(
    schemaDefinitionClass: Class<S>,
    conceptEntries: ResolvedConcepts
): DomainUnitProcessTargetFilesData<S> {


    private val targetFilesCollector: TargetFilesCollector = ListTargetFilesCollectorImpl()
    private val schemaInstance = ProxyCreator.createProxy(schemaDefinitionClass, SchemaInstanceInvocationHandler(conceptEntries))

    override fun getSchemaInstance(): S {
        return schemaInstance
    }


    override fun getTargetFilesCollector(): TargetFilesCollector {
        return targetFilesCollector
    }
}
