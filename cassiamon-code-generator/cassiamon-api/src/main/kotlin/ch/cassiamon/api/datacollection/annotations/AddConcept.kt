package ch.cassiamon.api.datacollection.annotations

import kotlin.reflect.KClass


@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class AddConcept(val clazz: KClass<*>)
