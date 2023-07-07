package ch.cassiamon.engine.process.datacollection.proxy

import ch.cassiamon.engine.process.datacollection.ConceptDataCollector
import ch.cassiamon.engine.proxy.InvocationHandlerHelper
import ch.cassiamon.engine.proxy.ProxyCreator
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

class DataCollectorInvocationHandler(private val dataCollector: ConceptDataCollector) : InvocationHandler {
    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any {
        if(DataCollectorInvocationHandlerHelper.isAddConceptAnnotated(method)) {
            val conceptDataBuilderClass = DataCollectorInvocationHandlerHelper.getConceptBuilderClazz(method)

            val conceptName = DataCollectorInvocationHandlerHelper.getConceptNameParameter(method, args)
            val conceptIdentifier = DataCollectorInvocationHandlerHelper.getConceptIdentifierParameter(method, args)
            val parentConceptIdentifier =
                DataCollectorInvocationHandlerHelper.getParentConceptIdentifierParameter(method, args)

            val conceptBuilder = dataCollector.existingOrNewConceptData(conceptName = conceptName, conceptIdentifier = conceptIdentifier, parentConceptIdentifier = parentConceptIdentifier)
            return ProxyCreator.createProxy(conceptDataBuilderClass.java, DataCollectorConceptBuilderInvocationHandler(conceptBuilder))
        }

        return InvocationHandlerHelper.handleObjectMethodsOrThrow(this, method)

    }
}
