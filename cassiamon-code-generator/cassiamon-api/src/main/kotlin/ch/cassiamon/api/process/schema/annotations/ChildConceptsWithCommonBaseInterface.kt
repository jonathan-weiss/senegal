package ch.cassiamon.api.process.schema.annotations

import kotlin.reflect.KClass


@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ChildConceptsWithCommonBaseInterface(val baseInterfaceClass: KClass<*>, val conceptClasses: Array<KClass<*>> = [])
