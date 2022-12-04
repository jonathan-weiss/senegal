package ch.senegal.engine.freemarker.nodetree

import ch.senegal.engine.TmpFileUtil
import ch.senegal.engine.freemarker.FreemarkerFileDescriptor
import ch.senegal.engine.freemarker.FreemarkerTemplateProcessor
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Test
import kotlin.io.path.readText

internal class TemplateModelNodeTest {

    private val testClasspathBase = "/ch/senegal/engine/freemarker/nodetree"
    private val expectedContentClasspath = "$testClasspathBase/template-model-node-template.result.txt"

    @Test
    fun processFileContentWithFreemarker() {
        val templateProcessor = FreemarkerTemplateProcessor(testClasspathBase)
        val targetFilePath = TmpFileUtil.createTempFile("freemarker-template-model-test")
        val model = mutableMapOf<String, List<TemplateModelNode>>()

        val entityBuilder = TemplateModelNodeBuilder.createNodeBuilder()
        entityBuilder.createAndAttachChildNodeBuilder().addProperty("subnodeText1", "qwert1")
        entityBuilder.createAndAttachChildNodeBuilder().addProperty("subnodeText2", "qwert2")

        model["topLevelNodes"] = listOf(
            entityBuilder
            .addProperty("myString", "foo")
            .addProperty("myStringTwo", "bar")
            .addProperty("myInt", 42)
            //.addProperty("myBoolean", true)
            .build()
        )


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

        val expectedContent = TemplateModelNodeTest::class.java
            .getResource(expectedContentClasspath)
            ?.readText() ?: fail("Could not read '$expectedContentClasspath'")
        assertEquals(expectedContent, fileDescriptor.targetFile.readText())
    }

}
