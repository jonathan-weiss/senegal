package ch.senegal.engine.properties

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.util.Properties

internal class PropertyBagTest {

    @Test
    fun getSingleParameterValue() {
        // arrange
        val properties = Properties()
        properties[PathParameterName.XmlDefinitionFile.propertyName] = "bar"
        val propertyBag = PropertyBag(properties)

        // act + assert
        assertEquals("bar", propertyBag.getParameterValue(PathParameterName.XmlDefinitionFile))
    }

    @Test
    fun getInexistentSingleParameterValue() {
        // arrange
        val propertyBag = PropertyBag(Properties())

        // act + assert
        assertEquals(null, propertyBag.getParameterValue(PathParameterName.XmlDefinitionFile))
    }

    @Test
    fun getInexistentMultiValueParameter() {
        // arrange
        val propertyBag = PropertyBag(Properties())

        // act
        val values = propertyBag.getParameterValueMap(StringParameterName.Placeholder)

        // assert
        assertEquals(0, values.size)
    }


    @Test
    fun getMultiValueParameter() {
        // arrange
        val properties = Properties()
        properties[StringParameterName.Placeholder.propertyName + ".foo"] = "bar"
        properties[StringParameterName.Placeholder.propertyName + ".flow"] = "grow"
        properties[StringParameterName.Placeholder.propertyName + ".flew"] = "fly"
        val propertyBag = PropertyBag(properties)

        // act
        val values = propertyBag.getParameterValueMap(StringParameterName.Placeholder)

        // assert
        assertEquals("bar", values["foo"])
        assertEquals("grow", values["flow"])
        assertEquals("fly", values["flew"])
    }

}
