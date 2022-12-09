package ch.senegal.engine.properties

import java.util.*

object PropertyParameterSource: ParameterSource {

    private val properties: Map<String, String>
    init {
        properties = getPropertiesFromFile()
    }

    override fun getParameterValue(parameterName: SenegalParameterName<*>): String? {
        return properties[parameterName.propertyName]
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

}
