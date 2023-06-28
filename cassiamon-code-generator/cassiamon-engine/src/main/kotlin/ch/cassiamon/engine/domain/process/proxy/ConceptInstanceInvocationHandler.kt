package ch.cassiamon.engine.domain.process.proxy

import ch.cassiamon.api.ConceptName
import ch.cassiamon.api.annotations.ChildConcepts
import ch.cassiamon.api.annotations.Concept
import ch.cassiamon.api.model.ConceptModelNode
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import kotlin.reflect.KClass

class ConceptInstanceInvocationHandler(private val conceptModelNode: ConceptModelNode) : InvocationHandler {
    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any? {
        if(InvocationHandlerHelper.isChildConceptAnnotated(method)) {
            val conceptClass = InvocationHandlerHelper.getChildConceptsClazz(method)
            val conceptName = InvocationHandlerHelper.getChildConceptsName(method)

            return conceptModelNode.children(conceptName)
                .map { ProxyCreator.createConceptProxy(conceptClass.java, it) }
        }

        if(InvocationHandlerHelper.isInputFacetAnnotated(method)) {
            val facetName = InvocationHandlerHelper.getInputFacetName(method)
            return conceptModelNode.templateFacetValues.facetValue(facetName.name)
        }

        throw IllegalStateException("Method $method not annotated.")
    }
}
