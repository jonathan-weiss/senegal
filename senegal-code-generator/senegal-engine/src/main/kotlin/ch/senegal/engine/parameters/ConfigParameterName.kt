package ch.senegal.engine.parameters

import java.nio.file.Path
import java.nio.file.Paths

sealed interface ConfigParameterName<T : Any> {
    val propertyName: String

    fun fromString(stringValue: String): T
}

enum class StringConfigParameterName(override val propertyName: String): ConfigParameterName<String> {
    Placeholder("placeholder")
    ;

    override fun fromString(stringValue: String): String {
        return stringValue
    }
}

enum class PathConfigParameterName(override val propertyName: String): ConfigParameterName<Path> {
    DefinitionDirectory("definitionDirectory"),
    DefaultOutputDirectory("defaultOutputDirectory"),
    XmlDefinitionFile("xmlDefinitionFile"),
    ;

    override fun fromString(stringValue: String): Path {
        return Paths.get(stringValue)
    }
}

enum class BooleanConfigParameterName(override val propertyName: String): ConfigParameterName<Boolean> {
    ;

    override fun fromString(stringValue: String): Boolean {
        return stringValue.lowercase() == "true"
    }
}

object ConfigParameterNames {
    fun allParameters(): List<ConfigParameterName<*>> {
        return StringConfigParameterName.values().toList() +
                PathConfigParameterName.values().toList() +
                BooleanConfigParameterName.values().toList() -
                StringConfigParameterName.Placeholder // remove, as it is only a prefix property
    }


}

