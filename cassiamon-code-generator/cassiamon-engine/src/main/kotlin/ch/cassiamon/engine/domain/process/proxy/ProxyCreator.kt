package ch.cassiamon.engine.domain.process.proxy

import ch.cassiamon.api.model.ConceptModelGraph
import ch.cassiamon.api.model.ConceptModelNode
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Proxy

object ProxyCreator {

    fun <S:Any> createSchemaProxy(schemaDefinitionClass: Class<S>, conceptModelGraph: ConceptModelGraph): S {
        return createProxy(schemaDefinitionClass, SchemaInstanceInvocationHandler(conceptModelGraph))
    }

    fun <C:Any> createConceptProxy(conceptDefinitionClass: Class<C>, conceptModelNode: ConceptModelNode): C {
        return createProxy(conceptDefinitionClass, ConceptInstanceInvocationHandler(conceptModelNode))
    }

    private fun <X:Any> createProxy(definitionClass: Class<X>, invocationHandler: InvocationHandler): X {
        @Suppress("UNCHECKED_CAST")
        return Proxy.newProxyInstance(
            this::class.java.classLoader, arrayOf<Class<*>>(definitionClass),
            invocationHandler
        ) as X // must be of type X as we declare it in the list of classes
    }

}
