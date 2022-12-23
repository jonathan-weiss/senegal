package ch.senegal.engine.parameters

import java.util.Properties

class PropertyBag(properties: Properties) {

    private val propertiesMap: Map<String, String>
    init {
        propertiesMap = getPropertyMap(properties)
    }


    fun getParameterValue(parameterName: SenegalParameterName<*>): String? {
        return propertiesMap[parameterName.propertyName]
    }

    fun getParameterValueMap(parameterName: SenegalParameterName<*>): Map<String,String> {
        return propertiesMap.entries
            .filter { it.key.startsWith(parameterName.propertyName + ".") }
            .associate { Pair(it.key.substring(parameterName.propertyName.length + 1), it.value) }
    }


    private fun getPropertyMap(props: Properties): Map<String, String> {
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
