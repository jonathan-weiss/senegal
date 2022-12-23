package ch.senegal.engine.parameters

import java.nio.file.Path
import java.nio.file.Paths

sealed interface SenegalParameterName<T : Any> {
    val propertyName: String

    fun fromString(stringValue: String): T
}

enum class StringParameterName(override val propertyName: String): SenegalParameterName<String> {
    Placeholder("placeholder")
    ;

    override fun fromString(stringValue: String): String {
        return stringValue
    }
}

enum class PathParameterName(override val propertyName: String): SenegalParameterName<Path> {
    DefinitionDirectory("definitionDirectory"),
    DefaultOutputDirectory("defaultOutputDirectory"),
    XmlDefinitionFile("xmlDefinitionFile"),
    ;

    override fun fromString(stringValue: String): Path {
        return Paths.get(stringValue)
    }
}

enum class BooleanParameterName(override val propertyName: String): SenegalParameterName<Boolean> {
    ;

    override fun fromString(stringValue: String): Boolean {
        return stringValue.lowercase() == "true"
    }
}

object ParameterNames {
    fun allParameters(): List<SenegalParameterName<*>> {
        return StringParameterName.values().toList() +
                PathParameterName.values().toList() +
                BooleanParameterName.values().toList() -
                StringParameterName.Placeholder // remove, as it is only a prefix property
    }


}

