package ch.senegal.engine.parameters.sources

import ch.senegal.engine.parameters.ParameterSource
import ch.senegal.engine.util.PropertiesToMapConverter
import java.util.*

object EnvironmentVariablesParameterSource: ParameterSource {

    private val propertyMap: Map<String, String> = PropertiesToMapConverter.convertToMap(
        getPropertiesFromEnvironmentVariables()
    )

    override fun getParameterMap(): Map<String, String> {
        return propertyMap
    }

    private fun getPropertiesFromEnvironmentVariables(): Properties {
        val props = Properties()
        System.getenv().forEach { props[it.key] = it.value }
        return props
    }

    override fun toString(): String {
        return "${super.toString()}:System-Properties=$propertyMap"
    }

}
