package ch.senegal.engine.properties

object SystemPropertyParameterSource: ParameterSource {

    private val properties: Map<String, String>
    init {
        properties = getPropertiesFromSystemProperties()
    }

    override fun getParameterValue(parameterName: SenegalParameterName<*>): String? {
        return properties[parameterName.propertyName]
    }

    private fun getPropertiesFromSystemProperties(): Map<String, String> {
        val props = System.getProperties()
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

    override fun toString(): String {
        return "${super.toString()}:System-Properties=$properties"
    }

}
