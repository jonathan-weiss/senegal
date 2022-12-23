package ch.senegal.engine.parameters

import ch.senegal.engine.parameters.sources.StaticParameterSource
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.nio.file.Paths

class ParameterReaderTest {

    @Test
    fun getParameter() {
        // arrange
        val staticParameterSource1 = StaticParameterSource(mapOf(
            PathConfigParameterName.DefinitionDirectory.propertyName to "/definition/directory/1",
            PathConfigParameterName.XmlDefinitionFile.propertyName to "/xml/definition/file/1"
        ))

        val staticParameterSource2 = StaticParameterSource(mapOf(
            PathConfigParameterName.DefinitionDirectory.propertyName to "/definition/directory/2",
            PathConfigParameterName.DefaultOutputDirectory.propertyName to "/default/output/directory/2",

        ))

        val parameterReader = ParameterReader(listOf(staticParameterSource1, staticParameterSource2))

        // act + assert
        Assertions.assertEquals(Paths.get("/definition/directory/1"), parameterReader.getParameter(PathConfigParameterName.DefinitionDirectory))
        Assertions.assertEquals(Paths.get("/xml/definition/file/1"), parameterReader.getParameter(PathConfigParameterName.XmlDefinitionFile))
        Assertions.assertEquals(Paths.get("/default/output/directory/2"), parameterReader.getParameter(PathConfigParameterName.DefaultOutputDirectory))
    }

    @Test
    fun getPlaceholders() {
        // arrange
        val staticParameterSource1 = StaticParameterSource(mapOf(
            createPlaceholderKey("placeholderX") to "/foo/bar/x1",
            createPlaceholderKey("placeholderY") to "/foo/bar/y1",
        ))

        val staticParameterSource2 = StaticParameterSource(mapOf(
            createPlaceholderKey("placeholderX") to "/foo/bar/x2",
            createPlaceholderKey("placeholderZ") to "/foo/bar/z2",
        ))

        val parameterReader = ParameterReader(listOf(staticParameterSource1, staticParameterSource2))

        // act + assert
        Assertions.assertEquals(3, parameterReader.getPlaceholders().size)
        Assertions.assertEquals("/foo/bar/x1", parameterReader.getPlaceholders()["placeholderX"])
        Assertions.assertEquals("/foo/bar/y1", parameterReader.getPlaceholders()["placeholderY"])
        Assertions.assertEquals("/foo/bar/z2", parameterReader.getPlaceholders()["placeholderZ"])
    }

    private fun createPlaceholderKey(suffix: String): String {
        return "${StringConfigParameterName.Placeholder.propertyName}.$suffix"
    }

}
