package ch.senegal.engine.model.converter

import ch.senegal.plugin.ConceptName
import ch.senegal.plugin.FacetName
import ch.senegal.plugin.FileFacet
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.nio.file.Path
import java.nio.file.Paths

internal class FileFacetConverterTest {

    @Test
    fun `convert String value to File Path`() {
        val facet = FileFacet(
            facetName = FacetName.of("DummyFacet"),
            enclosingConceptName = ConceptName.of("DummyConcept"),
            isOnlyCalculated = false,
            enhanceFacetValue = { _, facetValue -> facetValue }
        )

        assertInstanceOf(Path::class.java, FileFacetConverter.convertStringValue(facet, "/foo/bar/file.txt").value)
        assertEquals(Paths.get("/foo/bar/file.txt"), FileFacetConverter.convertStringValue(facet, "/foo/bar/file.txt").value)
        assertEquals(Paths.get("file.txt"), FileFacetConverter.convertStringValue(facet, "file.txt").value)
    }

}
