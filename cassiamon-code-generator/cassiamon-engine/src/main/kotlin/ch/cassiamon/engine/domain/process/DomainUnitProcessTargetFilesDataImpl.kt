package ch.cassiamon.engine.domain.process

import ch.cassiamon.api.registration.DomainUnitProcessTargetFilesData
import ch.cassiamon.api.registration.TargetFilesCollector
import ch.cassiamon.api.schema.SchemaAccess
import ch.cassiamon.engine.ProcessSession
import ch.cassiamon.engine.domain.process.proxy.ProxyCreator

class DomainUnitProcessTargetFilesDataImpl<S: Any>(
    processSession: ProcessSession,
    private val schemaDefinitionClass: Class<S>,
    schema: SchemaAccess,
    conceptEntries: Concepts
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
