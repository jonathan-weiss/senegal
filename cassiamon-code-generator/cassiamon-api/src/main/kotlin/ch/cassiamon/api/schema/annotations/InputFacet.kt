package ch.cassiamon.api.schema.annotations

import kotlin.reflect.KClass


@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class InputFacet(val inputFacetName: String)
