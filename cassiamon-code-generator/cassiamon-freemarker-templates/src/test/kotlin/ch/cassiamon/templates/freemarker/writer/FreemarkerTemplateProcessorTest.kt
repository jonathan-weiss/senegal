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
              className (direct access): MyPerson
              packageName: ch.cassiamon.engine.freemarker
              packageName (direct access): ch.cassiamon.engine.freemarker
              Facets:
                FacetName: PackageName: ch.cassiamon.engine.freemarker
                FacetName: ClassName: MyPerson
              Children:
                conceptName: TestChildConcept
                conceptIdentifier: MyChild1TestConceptIdentifier
                conceptName: TestChildConcept
                conceptIdentifier: MyChild2TestConceptIdentifier

    """.trimIndent()

    @Test
    fun processFileContentWithFreemarker() {
        val templateProcessor = FreemarkerTemplateProcessor("")
        val testConceptName = ConceptName.of("TestConcept")
        val testChildConceptName = ConceptName.of("TestChildConcept")

        val testChildNode1 = SimpleConceptModelNode(
            conceptName = testChildConceptName,
            conceptIdentifier = ConceptIdentifier.of("MyChild1TestConceptIdentifier"),
            templateFacetValues = MapConceptModelNodeTemplateFacetValues(emptyMap())
        )

        val testChildNode2 = SimpleConceptModelNode(
            conceptName = testChildConceptName,
            conceptIdentifier = ConceptIdentifier.of("MyChild2TestConceptIdentifier"),
            templateFacetValues = MapConceptModelNodeTemplateFacetValues(emptyMap())
        )

        val testConceptModelNode = SimpleConceptModelNode(
            conceptName = testConceptName,
            conceptIdentifier = ConceptIdentifier.of("MyTestConceptIdentifier"),
            templateFacetValues = MapConceptModelNodeTemplateFacetValues(mapOf<String, Any>(
                "PackageName" to "ch.cassiamon.engine.freemarker",
                "ClassName" to "MyPerson",
            )),
            children = listOf(testChildNode1, testChildNode2)
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
            return valueMap.keys.map { FacetName.of(it) }.toSet()
        }

        override fun facetValue(key: String): Any? {
            return valueMap[key]
        }

    }
    class SimpleConceptModelNode(
        override val conceptName: ConceptName,
        override val conceptIdentifier: ConceptIdentifier,
        override val templateFacetValues: ConceptModelNodeTemplateFacetValues,
        private val children: List<ConceptModelNode> = emptyList()
    ) : ConceptModelNode {
        override fun parent(): ConceptModelNode? {
            return null
        }

        override fun allChildren(): List<ConceptModelNode> {
            return children
        }

        override fun children(conceptName: ConceptName): List<ConceptModelNode> {
            return emptyList()
        }
    }

    private fun byteIteratorAsString(byteIterator: ByteIterator): String {
        val byteList : MutableList<Byte> = mutableListOf()
        byteIterator.forEach { byte: Byte -> byteList.add(byte) }
        return byteList.toByteArray().toString(Charset.defaultCharset())
    }

}
