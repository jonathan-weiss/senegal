package ch.cassiamon.engine.process.schema.proxy

import ch.cassiamon.api.process.schema.ConceptIdentifier
import ch.cassiamon.engine.process.conceptgraph.ConceptNode
import ch.cassiamon.engine.proxy.InvocationHandlerHelper
import ch.cassiamon.engine.proxy.ProxyCreator
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import kotlin.reflect.KClass

class SchemaConceptInstanceInvocationHandler(private val conceptNode: ConceptNode) : InvocationHandler {
    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any? {
        if(SchemaInvocationHandlerHelper.isAnnotatedWithChildConcept(method)
            || SchemaInvocationHandlerHelper.isAnnotatedWithChildConceptWithCommonBaseInterface(method)) {

            return SchemaInvocationHandlerHelper.mapToProxy(method, conceptNode) { interfaceClass: KClass<*>, childConceptNode: ConceptNode ->
                ProxyCreator.createProxy(interfaceClass.java, SchemaConceptInstanceInvocationHandler(childConceptNode))
            }
        }

        if(SchemaInvocationHandlerHelper.isInputFacetAnnotated(method)) {
            val facetName = SchemaInvocationHandlerHelper.getInputFacetName(method)
            val facetValue = conceptNode.facetValues[facetName]
            return if(facetValue is ConceptNode) {
                val referencedInterfaceClass = SchemaInvocationHandlerHelper.validatedMethod(method).returnType
                ProxyCreator.createProxy(referencedInterfaceClass, SchemaConceptInstanceInvocationHandler(facetValue))
            } else {
                facetValue
            }

        }

        if(SchemaInvocationHandlerHelper.isConceptIdentifierAnnotated(method)) {
            return when(SchemaInvocationHandlerHelper.validatedMethod(method).returnType.kotlin) {
                String::class -> conceptNode.conceptIdentifier.name
                ConceptIdentifier::class -> conceptNode.conceptIdentifier
                else -> throw IllegalStateException("Unsupported type for conceptIdentifier method.")
            }
        }

        if(SchemaInvocationHandlerHelper.isParentConceptAnnotated(method)) {
            val parentConceptNode = conceptNode.parentConceptNode ?: throw IllegalStateException("Parent concept node was null.")
            val parentInterfaceClass = SchemaInvocationHandlerHelper.validatedMethod(method).returnType
            return ProxyCreator.createProxy(parentInterfaceClass, SchemaConceptInstanceInvocationHandler(parentConceptNode))
        }

        return InvocationHandlerHelper.handleObjectMethodsOrThrow(this, method)
    }
}
