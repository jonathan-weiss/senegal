package ch.cassiamon.api.process.schema.annotations


@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class InputFacet(val inputFacetName: String)
