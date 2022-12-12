package ch.senegal.plugin

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.nio.file.Path
import java.nio.file.Paths

internal class FacetTypeTest {

    @Test
    fun valueFromStringForTextFacetType() {
        assertInstanceOf(String::class.java, TextFacetType.facetValueFromString("text").value)
        assertEquals("text", TextFacetType.facetValueFromString("text").value)
        assertEquals("", TextFacetType.facetValueFromString("").value)
    }

    @Test
    fun valueFromStringForEnumerationFacetType() {
        val enumerationFacetType = EnumerationFacetType(listOf(
            FacetTypeEnumerationValue("sure"),
            FacetTypeEnumerationValue("maybe"),
            FacetTypeEnumerationValue("impossible")
        ))
        assertInstanceOf(String::class.java, enumerationFacetType.facetValueFromString("sure").value)
        assertEquals("sure", enumerationFacetType.facetValueFromString("sure").value)
        assertEquals("maybe", enumerationFacetType.facetValueFromString("maybe").value)
        assertEquals("impossible", enumerationFacetType.facetValueFromString("impossible").value)
        assertThrows(IllegalArgumentException::class.java) { -> enumerationFacetType.facetValueFromString("other").value }
    }

    @Test
    fun valueFromStringForIntegerNumberFacetType() {
        assertInstanceOf(Integer::class.java, IntegerNumberFacetType.facetValueFromString("25").value)
        assertEquals(25, IntegerNumberFacetType.facetValueFromString("25").value)
        assertEquals(0, IntegerNumberFacetType.facetValueFromString("0").value)
        assertEquals(-25, IntegerNumberFacetType.facetValueFromString("-25").value)
        assertThrows(IllegalArgumentException::class.java) { -> IntegerNumberFacetType.facetValueFromString("myNoneNumberValue").value }
        assertThrows(IllegalArgumentException::class.java) { -> IntegerNumberFacetType.facetValueFromString("").value }
    }

    @Test
    fun valueFromStringForBooleanFacetType() {
        assertInstanceOf(java.lang.Boolean::class.java, BooleanFacetType.facetValueFromString("true").value)
        assertEquals(true, BooleanFacetType.facetValueFromString("TRUE").value)
        assertEquals(true, BooleanFacetType.facetValueFromString("True").value)
        assertEquals(true, BooleanFacetType.facetValueFromString("true").value)
        assertEquals(false, BooleanFacetType.facetValueFromString("FALSE").value)
        assertEquals(false, BooleanFacetType.facetValueFromString("False").value)
        assertEquals(false, BooleanFacetType.facetValueFromString("false").value)
        assertEquals(false, BooleanFacetType.facetValueFromString("yes").value)
        assertEquals(false, BooleanFacetType.facetValueFromString("no").value)
        assertEquals(false, BooleanFacetType.facetValueFromString("1").value)
        assertEquals(false, BooleanFacetType.facetValueFromString("0").value)
        assertEquals(false, BooleanFacetType.facetValueFromString("").value)
        assertEquals(false, BooleanFacetType.facetValueFromString("otherText").value)
    }

    @Test
    fun valueFromStringForFileFacetType() {
        assertInstanceOf(Path::class.java, FileFacetType.facetValueFromString("/foo/bar/file.txt").value)
        assertEquals(Paths.get("/foo/bar/file.txt"), FileFacetType.facetValueFromString("/foo/bar/file.txt").value)
        assertEquals(Paths.get("file.txt"), FileFacetType.facetValueFromString("file.txt").value)
    }

    @Test
    fun valueFromStringForDirectoryFacetType() {
        assertInstanceOf(Path::class.java, DirectoryFacetType.facetValueFromString("/foo/bar").value)
        assertEquals(Paths.get("/foo/bar"), DirectoryFacetType.facetValueFromString("/foo/bar").value)
        assertEquals(Paths.get(""), DirectoryFacetType.facetValueFromString("").value)
    }

}
