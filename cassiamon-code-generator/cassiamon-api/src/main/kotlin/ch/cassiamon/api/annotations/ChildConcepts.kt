package ch.cassiamon.api.annotations

import kotlin.reflect.KClass


@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ChildConcepts(val clazz: KClass<*>)
