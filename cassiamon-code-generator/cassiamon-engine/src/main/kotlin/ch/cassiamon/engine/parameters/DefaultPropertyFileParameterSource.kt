package ch.cassiamon.engine.parameters

import ch.cassiamon.engine.virtualfilesystem.VirtualFileSystem
import java.util.*

class DefaultPropertyFileParameterSource(private val virtualFileSystem: VirtualFileSystem) : ParameterSource {

    private val resourceName = "/cassiamon.properties"
    private val propertyMap: Map<String, String> = PropertiesToMapConverter.convertToMap(getPropertiesFromFile())

    override fun getParameterMap(): Map<String, String> {
        return propertyMap
    }

    private fun getPropertiesFromFile(): Properties {
        val props = Properties()

        virtualFileSystem.classpathResourceAsInputStream(resourceName).use {
            props.load(it)
        }
        return props
    }

    override fun toString(): String {
        return "${super.toString()}:$resourceName=$propertyMap"
    }

}
