package ch.cassiamon.api.annotations.datacollector

import kotlin.reflect.KClass


@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class AddConcept(val clazz: KClass<*>)
