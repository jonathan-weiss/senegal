package ch.cassiamon.engine.process.schema.proxy

import ch.cassiamon.engine.process.conceptgraph.ConceptNode
import ch.cassiamon.engine.proxy.InvocationHandlerHelper
import ch.cassiamon.engine.proxy.ProxyCreator
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

class SchemaConceptInstanceInvocationHandler(private val conceptNode: ConceptNode) : InvocationHandler {
    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any? {
        if(SchemaInvocationHandlerHelper.isChildConceptAnnotated(method)) {
            val conceptClass = SchemaInvocationHandlerHelper.getChildConceptsClazz(method)
            val conceptName = SchemaInvocationHandlerHelper.getChildConceptsName(method)

            return conceptNode.children(conceptName)
                .map { ProxyCreator.createProxy(conceptClass.java, SchemaConceptInstanceInvocationHandler(it)) }
        }

        if(SchemaInvocationHandlerHelper.isInputFacetAnnotated(method)) {
            val facetName = SchemaInvocationHandlerHelper.getInputFacetName(method)
            return conceptNode.facetValues[facetName]
        }

        return InvocationHandlerHelper.handleObjectMethodsOrThrow(this, method)
    }
}
