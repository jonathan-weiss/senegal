package ch.senegal.engine.model.converter

import ch.senegal.plugin.*
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class StringEnumFacetConverterTest {

    @Test
    fun valueFromStringForEnumerationFacetType() {
        val enumerationOptions = listOf(
            StringEnumerationFacetOption("sure"),
            StringEnumerationFacetOption("maybe"),
            StringEnumerationFacetOption("impossible")
        )

        val facet = StringEnumerationFacet(
            facetName = FacetName.of("DummyFacet"),
            enclosingConceptName = ConceptName.of("DummyConcept"),
            isOnlyCalculated = false,
            enumerationOptions = enumerationOptions,
            enhanceFacetValue = { _, facetValue -> facetValue }
        )
        assertInstanceOf(String::class.java, StringEnumFacetConverter.convertStringValue(facet, "sure").value)
        assertEquals("sure", StringEnumFacetConverter.convertStringValue(facet, "sure").value)
        assertEquals("maybe", StringEnumFacetConverter.convertStringValue(facet, "maybe").value)
        assertEquals("impossible", StringEnumFacetConverter.convertStringValue(facet, "impossible").value)
        assertThrows(IllegalArgumentException::class.java) { -> StringEnumFacetConverter.convertStringValue(
            facet,
            "other"
        ).value }
    }

}
