package ch.cassiamon.engine.domain.datacollection.proxy

import ch.cassiamon.engine.domain.datacollection.ConceptDataCollector
import ch.cassiamon.engine.proxy.InvocationHandlerHelper
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

class DataCollectorConceptBuilderInvocationHandler(private val dataCollectorConceptBuilder: ConceptDataCollector.MutableConceptData) : InvocationHandler {
    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any {
        if(DataCollectorInvocationHandlerHelper.isAddFacetAnnotated(method)) {
            val facetName = DataCollectorInvocationHandlerHelper.getFacetNameParameter(method, args)
            val facetValue = DataCollectorInvocationHandlerHelper.getFacetValueParameter(method, args)

            dataCollectorConceptBuilder.addFacetValue(facetName = facetName, facetValue = facetValue)
            return InvocationHandlerHelper.requiredProxy(proxy, method)
        } else if(DataCollectorInvocationHandlerHelper.isCommitConceptAnnotated(method)) {
            dataCollectorConceptBuilder.attach()
            return InvocationHandlerHelper.requiredProxy(proxy, method)
        }

        return InvocationHandlerHelper.handleObjectMethodsOrThrow(this, method)
    }
}
