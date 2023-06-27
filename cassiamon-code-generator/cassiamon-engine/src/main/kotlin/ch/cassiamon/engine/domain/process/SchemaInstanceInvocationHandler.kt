package ch.cassiamon.engine.domain.process

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

class SchemaInstanceInvocationHandler: InvocationHandler {
    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any {
        TODO("Method $method for proxy not yet implemented ($args)")
    }
}
