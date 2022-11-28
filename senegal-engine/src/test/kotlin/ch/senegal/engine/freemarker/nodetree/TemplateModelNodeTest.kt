package ch.senegal.engine.freemarker.nodetree

import ch.senegal.engine.TmpFileUtil
import ch.senegal.engine.freemarker.FreemarkerFileDescriptor
import ch.senegal.engine.freemarker.FreemarkerTemplateProcessor
import org.junit.jupiter.api.Test
import kotlin.io.path.readText

internal class TemplateModelNodeTest {

    @Test
    fun processFileContentWithFreemarker() {
        val templateProcessor = FreemarkerTemplateProcessor("/ch/senegal/engine/freemarker")
        val targetFilePath = TmpFileUtil.createTempFile("freemarker-template-model-test")
        val model = mutableMapOf<String, List<TemplateModelNode>>()

        val entityBuilder = EntityModelNode.createEntityBuilder()
        entityBuilder.createAndAttachSubNodeBuilder().addProperty("subnodeText1", "qwert1")
        entityBuilder.createAndAttachSubNodeBuilder().addProperty("subnodeText2", "qwert2")

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
            templateClassPath = "node-template.ftl"
        )
        templateProcessor.processFileContentWithFreemarker(fileDescriptor)
        println("The template has been created at ${fileDescriptor.targetFile}")
        println("Content:")
        println("------------------------------------------------")
        println(fileDescriptor.targetFile.readText())
        println("------------------------------------------------")
    }

}
