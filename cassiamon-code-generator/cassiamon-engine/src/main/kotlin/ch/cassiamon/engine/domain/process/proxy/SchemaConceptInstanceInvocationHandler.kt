package ch.cassiamon.engine.domain.process.proxy

import ch.cassiamon.engine.domain.process.Concepts
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

class SchemaConceptInstanceInvocationHandler(private val conceptEntry: Concepts.ConceptEntry) : InvocationHandler {
    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any? {
        if(SchemaInvocationHandlerHelper.isChildConceptAnnotated(method)) {
            val conceptClass = SchemaInvocationHandlerHelper.getChildConceptsClazz(method)
            val conceptName = SchemaInvocationHandlerHelper.getChildConceptsName(method)

            return conceptEntry.children(conceptName)
                .map { ProxyCreator.createSchemaConceptProxy(conceptClass.java, it) }
        }

        if(SchemaInvocationHandlerHelper.isInputFacetAnnotated(method)) {
            val facetName = SchemaInvocationHandlerHelper.getInputFacetName(method)
            return conceptEntry.facetValues[facetName]
        }

        return InvocationHandlerHelper.handleObjectMethodsOrThrow(this, method)
    }
}
