package ch.senegal.engine.freemarker.templatemodel

import ch.senegal.engine.TmpFileUtil
import ch.senegal.engine.freemarker.templateengine.FreemarkerTemplateProcessor
import ch.senegal.engine.model.FacetValue
import ch.senegal.engine.model.MutableModelTree
import ch.senegal.engine.plugin.*
import ch.senegal.engine.plugin.resolver.PluginResolver
import ch.senegal.engine.plugin.resolver.ResolvedConcept
import ch.senegal.engine.plugin.resolver.ResolvedPlugins
import ch.senegal.engine.plugin.resolver.ResolvedFacet
import ch.senegal.engine.virtualfilesystem.PhysicalFilesVirtualFileSystem
import ch.senegal.plugin.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Test
import kotlin.io.path.readText

internal class FreemarkerTemplateFileDescriptionCreatorTest {

    private val testClasspathBase = ""

    private val expectedContent = """
        Properties:
            TestKotlinModelClassname: TestEntityClassNumberOne
        direct access: TestEntityClassNumberOne

        SubNodes:

            Properties:
                TestKotlinFieldName: myField
                TestKotlinFieldType: kotlin.String

        Properties:
            TestKotlinModelClassname: TestEntityClassNumberTwo
            TestKotlinModelPackage: ch.senegal.test
        direct access: TestEntityClassNumberTwo

        SubNodes:


    """.trimIndent()

    @Test
    fun processFileContentWithFreemarker() {
        // arrange
        val virtualFileSystem = PhysicalFilesVirtualFileSystem()
        val defaultOutputPath = TmpFileUtil.createTempDirectory()
        val resolvedPlugins = createResolvedPlugin()
        val modelTree = createModelTree(resolvedPlugins)

        // act
        val listOfTemplateTargets = TemplateFileDescriptionCreator
            .createTemplateTargets(modelTree, resolvedPlugins, defaultOutputPath)

        val templateProcessor = FreemarkerTemplateProcessor(testClasspathBase)
        templateProcessor.processFileContentWithFreemarker(listOfTemplateTargets, virtualFileSystem)

        // assert
        assertEquals(2, listOfTemplateTargets.size)
        val fileDescriptor = listOfTemplateTargets.first()

        println("The template has been created at ${fileDescriptor.targetFile}")
        println("Content:")
        println("------------------------------------------------")
        println(fileDescriptor.targetFile.readText())
        println("------------------------------------------------")

        assertEquals(expectedContent, fileDescriptor.targetFile.readText())
    }

    private fun createModelTree(resolvedPlugins: ResolvedPlugins): MutableModelTree {

        val modelTree = MutableModelTree(resolvedPlugins = resolvedPlugins)

        val resolvedTestEntity = findResolvedConcept(resolvedPlugins, TestEntityConcept.conceptName)
        val testEntityNode1 = modelTree.createAndAddMutableModelNode(resolvedTestEntity)
        testEntityNode1.addFacetValue(
            resolvedFacet = findResolvedFacet(resolvedPlugins, TestKotlinModelPurpose.purposeName, testClassnameFacet.facetName),
            facetValue = FacetValue.of("TestEntityClassNumberOne")
        )
        val testEntityNode2 = modelTree.createAndAddMutableModelNode(resolvedTestEntity)
        testEntityNode2.addFacetValue(
            resolvedFacet = findResolvedFacet(resolvedPlugins, TestKotlinModelPurpose.purposeName, testClassnameFacet.facetName),
            facetValue = FacetValue.of("TestEntityClassNumberTwo")
        )
        testEntityNode2.addFacetValue(
            resolvedFacet = findResolvedFacet(resolvedPlugins, TestKotlinModelPurpose.purposeName, testPackageFacet.facetName),
            facetValue = FacetValue.of("ch.senegal.test")
        )

        val resolvedTestEntityAttribute = findResolvedConcept(resolvedPlugins, TestEntityAttributeConcept.conceptName)
        val testEntityAttributeNode1 = testEntityNode1.createAndAddMutableModelNode(resolvedTestEntityAttribute)
        testEntityAttributeNode1.addFacetValue(
            resolvedFacet = findResolvedFacet(resolvedPlugins, TestKotlinFieldPurpose.purposeName, testFieldNameFacet.facetName),
            facetValue = FacetValue.of("myField")
        )
        testEntityAttributeNode1.addFacetValue(
            resolvedFacet = findResolvedFacet(resolvedPlugins, TestKotlinFieldPurpose.purposeName, testFieldTypeFacet.facetName),
            facetValue = FacetValue.of("kotlin.String")
        )

        return modelTree
    }

    private fun createResolvedPlugin(): ResolvedPlugins {
        val entity = TestEntityConcept
        val entityAttribute = TestEntityAttributeConcept
        val mapper = TestMapperConcept
        val kotlinModel = TestKotlinModelPurpose
        val kotlinModelField = TestKotlinFieldPurpose

        val plugins: Set<Plugin> = setOf(entity, entityAttribute, mapper, kotlinModel, kotlinModelField)
        return PluginResolver.resolvePlugins(plugins)
    }

    private fun findResolvedConcept(resolvedPlugins: ResolvedPlugins, conceptName: ConceptName): ResolvedConcept {
        return resolvedPlugins.getConceptByConceptName(conceptName)
            ?: throw IllegalArgumentException("Concept not found ${conceptName.name}")
    }

    private fun findResolvedFacet(resolvedPlugins: ResolvedPlugins, purposeName: PurposeName, facetName: FacetName): ResolvedFacet {
        return resolvedPlugins.getResolvedFacet(purposeName, facetName)
            ?: throw IllegalArgumentException("Facet not found for purpose '${purposeName.name}' and facet '${facetName.name}'")
    }

}
