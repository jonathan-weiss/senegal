package ch.cassiamon.engine.domain.process.proxy

import ch.cassiamon.engine.domain.process.Concepts
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

class SchemaInstanceInvocationHandler(private val conceptModelGraph: Concepts) : InvocationHandler {
    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any {
        val conceptClass = InvocationHandlerHelper.getChildConceptsClazz(method)
        val conceptName = InvocationHandlerHelper.getChildConceptsName(method)

        return conceptModelGraph.conceptsByConceptName(conceptName)
            .map { ProxyCreator.createConceptProxy(conceptClass.java, it) }
    }
}
