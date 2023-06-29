package ch.cassiamon.engine.domain.process.proxy

import ch.cassiamon.engine.domain.process.Concepts
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

class ConceptInstanceInvocationHandler(private val conceptEntry: Concepts.ConceptEntry) : InvocationHandler {
    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any? {
        if(InvocationHandlerHelper.isChildConceptAnnotated(method)) {
            val conceptClass = InvocationHandlerHelper.getChildConceptsClazz(method)
            val conceptName = InvocationHandlerHelper.getChildConceptsName(method)

            return conceptEntry.children(conceptName)
                .map { ProxyCreator.createConceptProxy(conceptClass.java, it) }
        }

        if(InvocationHandlerHelper.isInputFacetAnnotated(method)) {
            val facetName = InvocationHandlerHelper.getInputFacetName(method)
            return conceptEntry.facetValues[facetName]
        }

        throw IllegalStateException("Method $method not annotated.")
    }
}
