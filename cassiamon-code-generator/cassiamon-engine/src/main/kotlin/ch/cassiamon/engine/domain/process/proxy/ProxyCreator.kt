package ch.cassiamon.engine.domain.process.proxy

import ch.cassiamon.engine.domain.process.Concepts
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Proxy

object ProxyCreator {

    fun <S:Any> createSchemaProxy(schemaDefinitionClass: Class<S>, conceptEntries: Concepts): S {
        return createProxy(schemaDefinitionClass, SchemaInstanceInvocationHandler(conceptEntries))
    }

    fun <C:Any> createConceptProxy(conceptDefinitionClass: Class<C>, conceptEntry: Concepts.ConceptEntry): C {
        return createProxy(conceptDefinitionClass, ConceptInstanceInvocationHandler(conceptEntry))
    }

    private fun <X:Any> createProxy(definitionClass: Class<X>, invocationHandler: InvocationHandler): X {
        @Suppress("UNCHECKED_CAST")
        return Proxy.newProxyInstance(
            this::class.java.classLoader, arrayOf<Class<*>>(definitionClass),
            invocationHandler
        ) as X // must be of type X as we declare it in the list of classes
    }

}
