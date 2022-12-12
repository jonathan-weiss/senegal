package ch.senegal.engine.properties

import java.util.*

object PropertyParameterSource: ParameterSource {

    private const val resourceName = "/senegal.properties"
    private val propertyBag: PropertyBag = PropertyBag(getPropertiesFromFile())

    override fun getParameterValue(parameterName: SenegalParameterName<*>): String? {
        return propertyBag.getParameterValue(parameterName)
    }

    override fun getParameterMap(parameterName: SenegalParameterName<*>): Map<String, String> {
        return propertyBag.getParameterValueMap(parameterName)
    }

    private fun getPropertiesFromFile(): Properties {
        val props = Properties()
        val propertiesStream = this.javaClass.getResourceAsStream(resourceName)
            ?: throw IllegalArgumentException("Resource with name '$resourceName' not found.")

        propertiesStream.use {
            props.load(it)
        }
        return props
    }

    override fun toString(): String {
        return "${super.toString()}:$resourceName=$propertyBag"
    }

}
