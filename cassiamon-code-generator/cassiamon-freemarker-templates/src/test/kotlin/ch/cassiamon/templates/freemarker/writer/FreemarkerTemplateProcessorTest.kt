package ch.cassiamon.templates.freemarker.writer

import ch.cassiamon.api.ConceptName
import ch.cassiamon.api.FacetName
import ch.cassiamon.api.model.ConceptIdentifier
import ch.cassiamon.api.model.ConceptModelNode
import ch.cassiamon.api.model.ConceptModelNodeTemplateFacetValues
import ch.cassiamon.api.model.facets.TemplateFacet
import ch.cassiamon.api.template.TargetGeneratedFileWithModel
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.nio.charset.Charset
import java.nio.file.Paths

internal class FreemarkerTemplateProcessorTest {

    private val expectedContent = """
        Test template:
        
            Properties:
              conceptName: TestConcept
              conceptIdentifier: MyTestConceptIdentifier
              className: MyPerson
              packageName: ch.senegal.engine.freemarker

    """.trimIndent()

    @Test
    fun processFileContentWithFreemarker() {
        val templateProcessor = FreemarkerTemplateProcessor("")
        val testConceptName = ConceptName.of("TestConcept")

        val testConceptModelNode = SimpleConceptModelNode(
            conceptName = testConceptName,
            conceptIdentifier = ConceptIdentifier.of("MyTestConceptIdentifier"),
            templateFacetValues = MapConceptModelNodeTemplateFacetValues(mapOf<String, Any>(
                "packageName" to "ch.senegal.engine.freemarker",
                "className" to "MyPerson",
            ))
        )

        val targetGeneratedFileWithModel = TargetGeneratedFileWithModel(
            targetFile = Paths.get("dummy-file.txt"),
            model = listOf(testConceptModelNode),
        )
        val contentByteIterator = templateProcessor.processWithFreemarker(
            targetGeneratedFileWithModel,
            "/ch/cassiamon/templates/freemarker/writer/test-template.ftl"
        )

        val content = byteIteratorAsString(contentByteIterator)
        assertEquals(expectedContent, content)
    }

    class MapConceptModelNodeTemplateFacetValues(private val valueMap: Map<String, Any>): ConceptModelNodeTemplateFacetValues {
        override fun <T> facetValue(templateFacet: TemplateFacet<T>): T {
            throw NotImplementedError("Mock NoOpConceptModelNodeTemplateFacetValues have this method not implemented.")
        }

        override fun allTemplateFacetNames(): Set<FacetName> {
            return emptySet()
        }

        override fun get(key: String): Any? {
            return valueMap[key]
        }

    }
    class SimpleConceptModelNode(
        override val conceptName: ConceptName,
        override val conceptIdentifier: ConceptIdentifier,
        override val templateFacetValues: ConceptModelNodeTemplateFacetValues
    ) : ConceptModelNode {
        override fun parent(): ConceptModelNode? {
            return null
        }

        override fun allChildren(): List<ConceptModelNode> {
            return emptyList()
        }

        override fun children(conceptName: ConceptName): List<ConceptModelNode> {
            return emptyList()
        }

        override fun get(key: String): Any? {
            return when(key) {
                "conceptName" -> conceptName.name
                "conceptIdentifier" -> conceptIdentifier.code
                "templateFacetValues" -> templateFacetValues
                else -> null
            }
        }
    }

    private fun byteIteratorAsString(byteIterator: ByteIterator): String {
        val byteList : MutableList<Byte> = mutableListOf()
        byteIterator.forEach { byte: Byte -> byteList.add(byte) }
        return byteList.toByteArray().toString(Charset.defaultCharset())
    }

}
