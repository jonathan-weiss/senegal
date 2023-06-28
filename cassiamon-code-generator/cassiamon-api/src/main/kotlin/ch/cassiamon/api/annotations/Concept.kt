package ch.cassiamon.api.annotations


@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Concept(val conceptName: String,)
