package ch.cassiamon.engine.process.datacollection.proxy

import ch.cassiamon.api.process.datacollection.ConceptData
import ch.cassiamon.engine.proxy.InvocationHandlerHelper
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

class DataCollectorConceptBuilderInvocationHandler(private val dataCollectorConceptBuilder: ConceptData) : InvocationHandler {
    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any {
        if(DataCollectorInvocationHandlerHelper.isAddFacetAnnotated(method)) {
            val facetName = DataCollectorInvocationHandlerHelper.getFacetNameParameter(method, args)
            val facetValue = DataCollectorInvocationHandlerHelper.getFacetValueParameter(method, args)

            dataCollectorConceptBuilder.addOrReplaceFacetValue(facetName = facetName, facetValue = facetValue)
            return InvocationHandlerHelper.requiredProxy(proxy, method)
        }

        return InvocationHandlerHelper.handleObjectMethodsOrThrow(this, method)
    }
}
