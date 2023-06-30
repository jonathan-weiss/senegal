package ch.cassiamon.engine.domain.process.proxy

import ch.cassiamon.engine.domain.datacollection.ConceptDataCollector
import java.lang.IllegalStateException
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

class DataCollectorInvocationHandler(private val dataCollector: ConceptDataCollector) : InvocationHandler {
    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any {
        if(DataCollectorInvocationHandlerHelper.isAddConceptAnnotated(method)) {
            val conceptDataBuilderClass = DataCollectorInvocationHandlerHelper.getConceptBuilderClazz(method)

            val conceptName = DataCollectorInvocationHandlerHelper.getConceptNameParameter(method, args)
            val conceptIdentifier = DataCollectorInvocationHandlerHelper.getConceptIdentifierParameter(method, args)
            val parentConceptIdentifier = DataCollectorInvocationHandlerHelper.getParentConceptIdentifierParameter(method, args)

            val conceptBuilder = dataCollector.newConceptData(conceptName = conceptName, conceptIdentifier = conceptIdentifier, parentConceptIdentifier = parentConceptIdentifier)
            return ProxyCreator.createDataCollectorConceptBuilderProxy(conceptDataBuilderClass.java, conceptBuilder)
        }

        return InvocationHandlerHelper.handleObjectMethodsOrThrow(this, method)

    }
}
