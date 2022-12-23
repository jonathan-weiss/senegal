package ch.senegal.engine.parameters.sources

import ch.senegal.engine.parameters.ParameterSource
import ch.senegal.engine.util.PropertiesToMapConverter
import java.util.*

object SystemPropertyParameterSource: ParameterSource {

    private val propertyMap: Map<String, String> = PropertiesToMapConverter.convertToMap(
        getPropertiesFromSystemProperties()
    )

    override fun getParameterMap(): Map<String, String> {
        return propertyMap
    }

    private fun getPropertiesFromSystemProperties(): Properties {
        return System.getProperties()
    }

    override fun toString(): String {
        return "${super.toString()}:System-Properties=$propertyMap"
    }

}
