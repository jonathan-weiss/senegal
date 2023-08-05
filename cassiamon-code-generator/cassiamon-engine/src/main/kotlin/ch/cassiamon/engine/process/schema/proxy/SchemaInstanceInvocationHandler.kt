package ch.cassiamon.engine.process.schema.proxy

import ch.cassiamon.engine.process.conceptgraph.ConceptGraph
import ch.cassiamon.engine.proxy.InvocationHandlerHelper
import ch.cassiamon.engine.proxy.ProxyCreator
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

class SchemaInstanceInvocationHandler(private val conceptGraph: ConceptGraph) : InvocationHandler {
    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any {
        if(SchemaInvocationHandlerHelper.isAnnotatedWithChildConcept(method)
            || SchemaInvocationHandlerHelper.isAnnotatedWithChildConceptWithCommonBaseInterface(method)) {
            val conceptNamesAndClasses = SchemaInvocationHandlerHelper.getChildConceptNamesWithInterfaceClass(method)
            return conceptNamesAndClasses.flatMap { (conceptName, conceptClass) -> conceptGraph.rootConceptsByConceptName(conceptName)
                .map { ProxyCreator.createProxy(conceptClass.java, SchemaConceptInstanceInvocationHandler(it)) }

            }
        }

        return InvocationHandlerHelper.handleObjectMethodsOrThrow(this, method)
    }
}
