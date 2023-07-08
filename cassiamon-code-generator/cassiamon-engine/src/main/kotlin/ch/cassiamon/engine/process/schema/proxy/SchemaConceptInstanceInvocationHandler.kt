package ch.cassiamon.engine.process.schema.proxy

import ch.cassiamon.engine.process.conceptgraph.ConceptGraph
import ch.cassiamon.engine.proxy.InvocationHandlerHelper
import ch.cassiamon.engine.proxy.ProxyCreator
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

class SchemaConceptInstanceInvocationHandler(private val resolvedConcept: ConceptGraph.ResolvedConcept) : InvocationHandler {
    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any? {
        if(SchemaInvocationHandlerHelper.isChildConceptAnnotated(method)) {
            val conceptClass = SchemaInvocationHandlerHelper.getChildConceptsClazz(method)
            val conceptName = SchemaInvocationHandlerHelper.getChildConceptsName(method)

            return resolvedConcept.children(conceptName)
                .map { ProxyCreator.createProxy(conceptClass.java, SchemaConceptInstanceInvocationHandler(it)) }
        }

        if(SchemaInvocationHandlerHelper.isInputFacetAnnotated(method)) {
            val facetName = SchemaInvocationHandlerHelper.getInputFacetName(method)
            return resolvedConcept.facetValues[facetName]
        }

        return InvocationHandlerHelper.handleObjectMethodsOrThrow(this, method)
    }
}
