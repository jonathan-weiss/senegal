package ch.senegal.engine.model.converter

import ch.senegal.plugin.ConceptName
import ch.senegal.plugin.FacetName
import ch.senegal.plugin.IntegerFacet
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class IntegerFacetConverterTest {

    @Test
    fun `convert String value to Int`() {
        val facet = IntegerFacet(
            facetName = FacetName.of("DummyFacet"),
            enclosingConceptName = ConceptName.of("DummyConcept"),
            isOnlyCalculated = false,
            enhanceFacetValue = { _, facetValue -> facetValue }
        )

        assertInstanceOf(Integer::class.java, IntegerFacetConverter.convertStringValue(facet, "25").value)
        assertEquals(25, IntegerFacetConverter.convertStringValue(facet, "25").value)
        assertEquals(0, IntegerFacetConverter.convertStringValue(facet, "0").value)
        assertEquals(-25, IntegerFacetConverter.convertStringValue(facet, "-25").value)
        assertThrows(IllegalArgumentException::class.java) { -> IntegerFacetConverter.convertStringValue(
            facet,
            "myNoneNumberValue"
        ).value }
        assertThrows(IllegalArgumentException::class.java) { -> IntegerFacetConverter.convertStringValue(facet, "").value }
    }

}
