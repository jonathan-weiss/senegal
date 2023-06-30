package ch.cassiamon.engine.domain.process

import ch.cassiamon.api.registration.DomainUnitProcessTargetFilesData
import ch.cassiamon.api.registration.TargetFilesCollector
import ch.cassiamon.engine.domain.process.conceptresolver.ResolvedConcepts
import ch.cassiamon.engine.domain.process.proxy.ProxyCreator

class DomainUnitProcessTargetFilesDataImpl<S: Any>(
    schemaDefinitionClass: Class<S>,
    conceptEntries: ResolvedConcepts
): DomainUnitProcessTargetFilesData<S> {


    private val targetFilesCollector: TargetFilesCollector = ListTargetFilesCollectorImpl()
    private val schemaInstance = ProxyCreator.createSchemaProxy(schemaDefinitionClass, conceptEntries)

    override fun getSchemaInstance(): S {
        return schemaInstance
    }


    override fun getTargetFilesCollector(): TargetFilesCollector {
        return targetFilesCollector
    }
}
