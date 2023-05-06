package ch.cassiamon.api.extensions


@JvmInline
value class ClasspathLocation private constructor(val classpath: String) {

    companion object {
        fun of(classpath: String): ClasspathLocation {
            return ClasspathLocation(classpath)
        }
    }
}
