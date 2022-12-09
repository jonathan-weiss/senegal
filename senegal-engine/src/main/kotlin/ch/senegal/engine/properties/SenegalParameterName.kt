package ch.senegal.engine.properties

import java.nio.file.Path
import java.nio.file.Paths
import kotlin.reflect.KClass

sealed interface SenegalParameterName<T : Any> {
    val propertyName: String

    fun fromString(stringValue: String): T
}

enum class StringParameterName(override val propertyName: String): SenegalParameterName<String> {
    SourceMode("sourceMode")
    ;

    override fun fromString(stringValue: String): String {
        return stringValue
    }
}

enum class PathParameterName(override val propertyName: String): SenegalParameterName<Path> {
    DefinitionDirectory("definitionDirectory"),
    DefaultOutputDirectory("defaultOutputDirectory"),
    ;

    override fun fromString(stringValue: String): Path {
        return Paths.get(stringValue)
    }
}

enum class BooleanParameterName(override val propertyName: String): SenegalParameterName<Boolean> {
    EngineEnabled("engineEnabled"),
    ;

    override fun fromString(stringValue: String): Boolean {
        return stringValue.lowercase() == "true"
    }
}

