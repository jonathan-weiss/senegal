package ch.cassiamon.engine.domain.process.proxy

import ch.cassiamon.engine.domain.process.Concepts
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

class SchemaInstanceInvocationHandler(private val conceptModelGraph: Concepts) : InvocationHandler {
    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any {
        if(SchemaInvocationHandlerHelper.isChildConceptAnnotated(method)) {
            val conceptClass = SchemaInvocationHandlerHelper.getChildConceptsClazz(method)
            val conceptName = SchemaInvocationHandlerHelper.getChildConceptsName(method)

            return conceptModelGraph.conceptsByConceptName(conceptName)
                .map { ProxyCreator.createSchemaConceptProxy(conceptClass.java, it) }
        }

        return InvocationHandlerHelper.handleObjectMethodsOrThrow(this, method)
    }
}
