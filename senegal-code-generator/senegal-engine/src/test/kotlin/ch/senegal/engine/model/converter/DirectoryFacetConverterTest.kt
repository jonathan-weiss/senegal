package ch.senegal.engine.model.converter

import ch.senegal.plugin.ConceptName
import ch.senegal.plugin.DirectoryFacet
import ch.senegal.plugin.FacetName
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.nio.file.Path
import java.nio.file.Paths

internal class DirectoryFacetConverterTest {

    @Test
    fun `convert String value to Directory Path`() {
        val facet = DirectoryFacet(
            facetName = FacetName.of("DummyFacet"),
            enclosingConceptName = ConceptName.of("DummyConcept"),
            isOnlyCalculated = false,
            enhanceFacetValue = { _, facetValue -> facetValue }
        )

        assertInstanceOf(Path::class.java, DirectoryFacetConverter.convertStringValue(facet, "/foo/bar").value)
        assertEquals(Paths.get("/foo/bar"), DirectoryFacetConverter.convertStringValue(facet, "/foo/bar").value)
        assertEquals(Paths.get(""), DirectoryFacetConverter.convertStringValue(facet, "").value)
    }

}
