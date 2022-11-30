package ch.senegal.engine.properties

import java.nio.file.Path
import java.nio.file.Paths
import java.util.Properties

object ArgumentReader {
    private enum class ArgumentName(val propertyName: String) {
        DefinitionDirectory("definitionDirectory");

    }

    private fun getPropertiesFromFile(): Map<String, String> {
        val resourceName = "/senegal.properties"
        val props = Properties()
        val propertiesStream = this.javaClass.getResourceAsStream(resourceName)
            ?: throw IllegalArgumentException("Resource with name '$resourceName' not found.")

        propertiesStream.use {
            props.load(it)
        }
        return props
            .map { (key: Any, value: Any) -> Pair(asString(key), asString(value)) }
            .toMap()

    }

    private fun asString(value: Any?): String {
        if (value !is String) {
            throw IllegalArgumentException("$value was not of type String.")
        }
        return value
    }

    private fun getArgument(key: ArgumentName): String {
        return getPropertiesFromFile()[key.propertyName]
            ?: throw IllegalArgumentException("Could not find argument for key $key.")
    }

    fun getDefinitionDirectory(): Path {
        return Paths.get(getArgument(ArgumentName.DefinitionDirectory))
    }
}
