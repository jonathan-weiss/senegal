package ch.cassiamon.api.process.schema.annotations


@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Concept(val conceptName: String,)