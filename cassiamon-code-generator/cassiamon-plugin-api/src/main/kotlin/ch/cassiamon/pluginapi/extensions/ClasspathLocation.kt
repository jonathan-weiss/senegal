package ch.cassiamon.pluginapi.extensions


@JvmInline
value class ClasspathLocation private constructor(val classpath: String) {

    companion object {
        fun of(classpath: String): ClasspathLocation {
            return ClasspathLocation(classpath)
        }
    }
}
