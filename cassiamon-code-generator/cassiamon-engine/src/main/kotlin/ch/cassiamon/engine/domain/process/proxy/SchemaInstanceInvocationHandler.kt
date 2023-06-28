package ch.cassiamon.engine.domain.process.proxy

import ch.cassiamon.api.model.ConceptModelGraph
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

class SchemaInstanceInvocationHandler(private val conceptModelGraph: ConceptModelGraph) : InvocationHandler {
    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any {
        val conceptClass = InvocationHandlerHelper.getChildConceptsClazz(method)
        val conceptName = InvocationHandlerHelper.getChildConceptsName(method)

        return conceptModelGraph.conceptModelNodesByConceptName(conceptName)
            .map { ProxyCreator.createConceptProxy(conceptClass.java, it) }
    }
}
