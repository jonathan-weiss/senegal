package ch.cassiamon.engine.process.schema.proxy

import ch.cassiamon.engine.process.conceptgraph.ConceptGraph
import ch.cassiamon.engine.process.conceptgraph.ConceptNode
import ch.cassiamon.engine.proxy.InvocationHandlerHelper
import ch.cassiamon.engine.proxy.ProxyCreator
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import kotlin.reflect.KClass

class SchemaInstanceInvocationHandler(private val conceptGraph: ConceptGraph) : InvocationHandler {
    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any {
        if(SchemaInvocationHandlerHelper.isAnnotatedWithChildConcept(method)
            || SchemaInvocationHandlerHelper.isAnnotatedWithChildConceptWithCommonBaseInterface(method)) {

            return SchemaInvocationHandlerHelper.mapToProxy(method, conceptGraph) { interfaceClass: KClass<*>, childConceptNode: ConceptNode ->
                ProxyCreator.createProxy(interfaceClass.java, SchemaConceptInstanceInvocationHandler(childConceptNode))
            }
        }

        return InvocationHandlerHelper.handleObjectMethodsOrThrow(this, method)
    }
}
