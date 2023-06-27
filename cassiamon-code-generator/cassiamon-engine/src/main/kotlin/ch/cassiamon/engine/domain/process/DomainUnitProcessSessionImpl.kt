package ch.cassiamon.engine.domain.process

import ch.cassiamon.api.registration.DomainUnitProcessSession
import ch.cassiamon.api.registration.InputSourceDataCollector
import ch.cassiamon.api.registration.InputSourceExtensionAccess
import ch.cassiamon.api.registration.TargetFilesCollector
import ch.cassiamon.api.schema.SchemaAccess
import ch.cassiamon.engine.ProcessSession
import ch.cassiamon.engine.domain.SchemaCreator
import ch.cassiamon.engine.extension.ExtensionAccessHolder
import ch.cassiamon.engine.inputsource.ModelInputDataCollector
import java.lang.reflect.Proxy

class DomainUnitProcessSessionImpl<S: Any>(processSession: ProcessSession, private val schemaDefinitionClass: Class<S>): DomainUnitProcessSession<S> {


    private val modelInputDataCollector: ModelInputDataCollector = ModelInputDataCollector()
    private val extensionAccess: ExtensionAccessHolder = ExtensionAccessHolder(processSession.fileSystemAccess, processSession.loggerFacade, processSession.parameterAccess, modelInputDataCollector)
    private val targetFilesCollector: TargetFilesCollector = ListTargetFilesCollectorImpl()
    private val inputSourceExtensionAccess = InputSourceExtensionAccessImpl(extensionAccess)
    private val schemaInstance = createProxy()


    init {
        extensionAccess.initializeSchema(getSchemaAccess())
    }

    private fun createProxy(): S {
        @Suppress("UNCHECKED_CAST")
        return Proxy.newProxyInstance(
            this::class.java.classLoader, arrayOf<Class<*>>(schemaDefinitionClass),
            SchemaInstanceInvocationHandler()
        ) as S // must be of type S as we declare it in the list of classes
    }

    override fun getSchemaInstance(): S {
        return schemaInstance
    }


    fun getSchemaAccess(): SchemaAccess {
        return SchemaCreator.createSchemaFromSchemaDefinitionClass(schemaDefinitionClass)
    }
    override fun getDataCollector(): InputSourceDataCollector {
        return modelInputDataCollector
    }

    override fun getInputDataExtensionAccess(): InputSourceExtensionAccess {
        return inputSourceExtensionAccess
    }

    override fun getTargetFilesCollector(): TargetFilesCollector {
        return targetFilesCollector
    }
}
