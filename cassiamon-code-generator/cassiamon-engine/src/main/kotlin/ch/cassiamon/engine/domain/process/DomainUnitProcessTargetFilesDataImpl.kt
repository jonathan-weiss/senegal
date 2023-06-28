package ch.cassiamon.engine.domain.process

import ch.cassiamon.api.model.ConceptModelGraph
import ch.cassiamon.api.registration.DomainUnitProcessTargetFilesData
import ch.cassiamon.api.registration.TargetFilesCollector
import ch.cassiamon.api.schema.SchemaAccess
import ch.cassiamon.engine.ProcessSession
import ch.cassiamon.engine.domain.process.proxy.ProxyCreator
import ch.cassiamon.engine.domain.process.proxy.SchemaInstanceInvocationHandler
import java.lang.reflect.Proxy

class DomainUnitProcessTargetFilesDataImpl<S: Any>(
    processSession: ProcessSession,
    private val schemaDefinitionClass: Class<S>,
    schema: SchemaAccess,
    conceptModelGraph: ConceptModelGraph
): DomainUnitProcessTargetFilesData<S> {


    private val targetFilesCollector: TargetFilesCollector = ListTargetFilesCollectorImpl()
    private val schemaInstance = ProxyCreator.createSchemaProxy(schemaDefinitionClass, conceptModelGraph)

    override fun getSchemaInstance(): S {
        return schemaInstance
    }


    override fun getTargetFilesCollector(): TargetFilesCollector {
        return targetFilesCollector
    }
}
