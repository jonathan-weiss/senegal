package ch.cassiamon.api.schema.annotations


@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Concept(val conceptName: String,)
