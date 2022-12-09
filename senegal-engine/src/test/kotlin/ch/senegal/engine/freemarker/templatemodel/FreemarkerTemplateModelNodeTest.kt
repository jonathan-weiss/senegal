package ch.senegal.engine.freemarker.templatemodel

import ch.senegal.engine.TmpFileUtil
import ch.senegal.engine.freemarker.templateengine.FreemarkerFileDescriptor
import ch.senegal.engine.freemarker.templateengine.FreemarkerTemplateProcessor
import ch.senegal.engine.model.Decoration
import ch.senegal.engine.model.ModelTree
import ch.senegal.engine.plugin.*
import ch.senegal.engine.plugin.resolver.PluginResolver
import ch.senegal.engine.plugin.resolver.ResolvedConcept
import ch.senegal.engine.plugin.resolver.ResolvedPlugins
import ch.senegal.engine.plugin.resolver.ResolvedPurposeDecor
import ch.senegal.plugin.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Test
import kotlin.io.path.readText

internal class FreemarkerTemplateModelNodeTest {

    private val testClasspathBase = "/ch/senegal/engine/freemarker"
    private val expectedContentClasspath = "$testClasspathBase/template-model-node-template.result.txt"

    @Test
    fun processFileContentWithFreemarker() {
        val templateProcessor = FreemarkerTemplateProcessor(testClasspathBase)
        val targetFilePath = TmpFileUtil.createTempFile("freemarker-template-model-test")
        val model = mutableMapOf<String, List<TemplateModelNode>>()
        model["topLevelNodes"] = createTemplateModelNodes()

        val fileDescriptor = FreemarkerFileDescriptor(
            targetFile = targetFilePath,
            model = model,
            templateClassPath = "template-model-node-template.ftl"
        )
        templateProcessor.processFileContentWithFreemarker(fileDescriptor)
        println("The template has been created at ${fileDescriptor.targetFile}")
        println("Content:")
        println("------------------------------------------------")
        println(fileDescriptor.targetFile.readText())
        println("------------------------------------------------")

        val expectedContent = FreemarkerTemplateModelNodeTest::class.java
            .getResource(expectedContentClasspath)
            ?.readText() ?: fail("Could not read '$expectedContentClasspath'")
        assertEquals(expectedContent, fileDescriptor.targetFile.readText())
    }

    private fun createTemplateModelNodes(): List<TemplateModelNode> {
        val resolvedPlugins = createResolvedPlugin()
        val modelTree = ModelTree(resolvedPlugins = resolvedPlugins)

        val resolvedTestEntity = findResolvedConcept(resolvedPlugins, TestEntityConcept.conceptName)
        val testEntityNode1 = modelTree.createAndAddModelNode(resolvedTestEntity)
        testEntityNode1.addDecoration(
            purposeDecor = findResolvedDecor(resolvedPlugins, TestKotlinModelPurpose.purposeName, TestClassnameDecor.decorName),
            decoration = Decoration("TestEntityClassNumberOne")
        )
        val testEntityNode2 = modelTree.createAndAddModelNode(resolvedTestEntity)
        testEntityNode2.addDecoration(
            purposeDecor = findResolvedDecor(resolvedPlugins, TestKotlinModelPurpose.purposeName, TestClassnameDecor.decorName),
            decoration = Decoration("TestEntityClassNumberTwo")
        )
        testEntityNode2.addDecoration(
            purposeDecor = findResolvedDecor(resolvedPlugins, TestKotlinModelPurpose.purposeName, TestPackageDecor.decorName),
            decoration = Decoration("ch.senegal.test")
        )

        val resolvedTestEntityAttribute = findResolvedConcept(resolvedPlugins, TestEntityAttributeConcept.conceptName)
        val testEntityAttributeNode1 = testEntityNode1.createAndAddModelNode(resolvedTestEntityAttribute)
        testEntityAttributeNode1.addDecoration(
            purposeDecor = findResolvedDecor(resolvedPlugins, TestKotlinFieldPurpose.purposeName, TestFieldNameDecor.decorName),
            decoration = Decoration("myField")
        )
        testEntityAttributeNode1.addDecoration(
            purposeDecor = findResolvedDecor(resolvedPlugins, TestKotlinFieldPurpose.purposeName, TestFieldTypeDecor.decorName),
            decoration = Decoration("kotlin.String")
        )

        return TemplateModelCreator.createTemplateModel(modelTree)

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

    private fun findResolvedDecor(resolvedPlugins: ResolvedPlugins, purposeName: PurposeName, decorName: DecorName): ResolvedPurposeDecor {
        return resolvedPlugins.getResolvedDecor(purposeName, decorName)
            ?: throw IllegalArgumentException("Decor not found for purpose '${purposeName.name}' and decor '${decorName.name}'")
    }

}
