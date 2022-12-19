package ch.senegal.engine.model.converter

import ch.senegal.plugin.BooleanFacet
import ch.senegal.plugin.ConceptName
import ch.senegal.plugin.FacetName
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class BooleanFacetConverterTest {

    @Test
    fun `convert String value to Boolean`() {
        val facet = BooleanFacet(
            facetName = FacetName.of("DummyFacet"),
            enclosingConceptName = ConceptName.of("DummyConcept"),
            isOnlyCalculated = false,
            enhanceFacetValue = { _, facetValue -> facetValue }
        )
        assertInstanceOf(java.lang.Boolean::class.java, BooleanFacetConverter.convertStringValue(facet, "true").value)
        assertEquals(true,  BooleanFacetConverter.convertStringValue(facet, "TRUE").value)
        assertEquals(true,  BooleanFacetConverter.convertStringValue(facet, "True").value)
        assertEquals(true,  BooleanFacetConverter.convertStringValue(facet, "true").value)
        assertEquals(false, BooleanFacetConverter.convertStringValue(facet, "FALSE").value)
        assertEquals(false, BooleanFacetConverter.convertStringValue(facet, "False").value)
        assertEquals(false, BooleanFacetConverter.convertStringValue(facet, "false").value)
        assertEquals(false, BooleanFacetConverter.convertStringValue(facet, "yes").value)
        assertEquals(false, BooleanFacetConverter.convertStringValue(facet, "no").value)
        assertEquals(false, BooleanFacetConverter.convertStringValue(facet, "1").value)
        assertEquals(false, BooleanFacetConverter.convertStringValue(facet, "0").value)
        assertEquals(false, BooleanFacetConverter.convertStringValue(facet, "").value)
        assertEquals(false, BooleanFacetConverter.convertStringValue(facet, "otherText").value)
    }
}
