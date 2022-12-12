package ch.senegal.engine.properties

import java.util.*

object EnvironmentVariablesParameterSource: ParameterSource {

    private val propertyBag: PropertyBag = PropertyBag(getPropertiesFromEnvironmentVariables())

    override fun getParameterValue(parameterName: SenegalParameterName<*>): String? {
        return propertyBag.getParameterValue(parameterName)
    }

    override fun getParameterMap(parameterName: SenegalParameterName<*>): Map<String, String> {
        return propertyBag.getParameterValueMap(parameterName)
    }

    private fun getPropertiesFromEnvironmentVariables(): Properties {
        val props = Properties()
        System.getenv().forEach { props[it.key] = it.value }
        return props
    }

    override fun toString(): String {
        return "${super.toString()}:System-Properties=$propertyBag"
    }

}
