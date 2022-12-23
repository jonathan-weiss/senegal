package ch.senegal.engine.util

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.util.*

class PropertiesToMapConverterTest {

    @Test
    fun getSingleParameterValue() {
        // arrange
        val properties = Properties()
        properties["foo"] = "bar"

        // act
        val map = PropertiesToMapConverter.convertToMap(properties)

        // assert
        assertEquals(1, map.size)
        assertEquals("bar", map["foo"])
    }

    @Test
    fun getInexistentSingleParameterValue() {
        // arrange
        val properties = Properties()
        properties["foo"] = "bar"

        // act
        val map = PropertiesToMapConverter.convertToMap(properties)

        // assert
        assertEquals(1, map.size)
        assertEquals(null, map["baz"])
    }
}
