package ch.senegal.engine.model.converter

import ch.senegal.plugin.BooleanFacet
import ch.senegal.plugin.ConceptName
import ch.senegal.plugin.FacetName
import ch.senegal.plugin.StringFacet
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class StringFacetConverterTest {

    @Test
    fun `convert String value to String`() {
        val facet = StringFacet(
            facetName = FacetName.of("DummyFacet"),
            enclosingConceptName = ConceptName.of("DummyConcept"),
            isOnlyCalculated = false,
            enhanceFacetValue = { _, facetValue -> facetValue }
        )

        assertInstanceOf(String::class.java, StringFacetConverter.convertStringValue(facet, "text").value)
        assertEquals("text", StringFacetConverter.convertStringValue(facet, "text").value)
        assertEquals("", StringFacetConverter.convertStringValue(facet, "").value)
    }
}
