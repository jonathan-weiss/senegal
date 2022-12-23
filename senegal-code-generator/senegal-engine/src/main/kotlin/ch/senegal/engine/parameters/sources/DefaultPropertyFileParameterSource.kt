package ch.senegal.engine.parameters.sources

import ch.senegal.engine.parameters.ParameterSource
import ch.senegal.engine.util.PropertiesToMapConverter
import java.util.*

object DefaultPropertyFileParameterSource : ParameterSource {

    private const val resourceName = "/senegal.properties"
    private val propertyMap: Map<String, String> = PropertiesToMapConverter.convertToMap(getPropertiesFromFile())

    override fun getParameterMap(): Map<String, String> {
        return propertyMap
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
        return "${super.toString()}:$resourceName=$propertyMap"
    }

}
