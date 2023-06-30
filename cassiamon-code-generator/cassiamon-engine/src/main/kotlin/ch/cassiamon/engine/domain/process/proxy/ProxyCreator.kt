package ch.cassiamon.engine.domain.process.proxy

import ch.cassiamon.engine.domain.datacollection.ConceptDataCollector
import ch.cassiamon.engine.domain.process.conceptresolver.ResolvedConcepts
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Proxy

object ProxyCreator {

    fun <I:Any> createDataCollectorProxy(dataCollectorDefinitionClass: Class<I>, conceptDataCollector: ConceptDataCollector): I {
        return createProxy(dataCollectorDefinitionClass, DataCollectorInvocationHandler(conceptDataCollector))
    }

    fun <I:Any> createDataCollectorConceptBuilderProxy(dataCollectorBuildDefinitionClass: Class<I>, conceptDataBuilder: ConceptDataCollector.MutableConceptData): I {
        return createProxy(dataCollectorBuildDefinitionClass, DataCollectorConceptBuilderInvocationHandler(conceptDataBuilder))
    }

    fun <S:Any> createSchemaProxy(schemaDefinitionClass: Class<S>, conceptEntries: ResolvedConcepts): S {
        return createProxy(schemaDefinitionClass, SchemaInstanceInvocationHandler(conceptEntries))
    }

    fun <C:Any> createSchemaConceptProxy(conceptDefinitionClass: Class<C>, resolvedConcept: ResolvedConcepts.ResolvedConcept): C {
        return createProxy(conceptDefinitionClass, SchemaConceptInstanceInvocationHandler(resolvedConcept))
    }

    private fun <X:Any> createProxy(definitionClass: Class<X>, invocationHandler: InvocationHandler): X {
        @Suppress("UNCHECKED_CAST")
        return Proxy.newProxyInstance(
            this::class.java.classLoader, arrayOf<Class<*>>(definitionClass),
            invocationHandler
        ) as X // must be of type X as we declare it in the list of classes
    }

}
