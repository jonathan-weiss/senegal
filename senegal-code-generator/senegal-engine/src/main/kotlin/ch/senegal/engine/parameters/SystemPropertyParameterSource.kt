package ch.senegal.engine.parameters

import java.util.*

object SystemPropertyParameterSource: ParameterSource {

    private val propertyBag: PropertyBag = PropertyBag(getPropertiesFromSystemProperties())

    override fun getParameterValue(parameterName: SenegalParameterName<*>): String? {
        return propertyBag.getParameterValue(parameterName)
    }

    override fun getParameterMap(parameterName: SenegalParameterName<*>): Map<String, String> {
        return propertyBag.getParameterValueMap(parameterName)
    }

    private fun getPropertiesFromSystemProperties(): Properties {
        return System.getProperties()
    }

    override fun toString(): String {
        return "${super.toString()}:System-Properties=$propertyBag"
    }

}
