package ch.senegal.engine.freemarker

import ch.senegal.engine.TmpFileUtil
import org.junit.jupiter.api.Test
import kotlin.io.path.readText

internal class FreemarkerTemplateProcessorTest {

    @Test
    fun processFileContentWithFreemarker() {
        val templateProcessor = FreemarkerTemplateProcessor("/ch/senegal/engine/freemarker")
        val targetFilePath = TmpFileUtil.createTempFile("freemarker-test")
        val model = mutableMapOf<String, Any>()
        model["packageName"] = "ch.senegal.engine.freemarker"
        model["className"] = "MyPerson"
        model["classFields"] = listOf(
            TestFieldDescription("firstname", "kotlin.String", true),
            TestFieldDescription("lastname", "kotlin.String", false),
        )
        val fileDescriptor = FreemarkerFileDescriptor(
            targetFile = targetFilePath,
            model = model,
            templateClassPath = "example-template.ftl"
        )
        templateProcessor.processFileContentWithFreemarker(fileDescriptor)
        println("The template has been created at ${fileDescriptor.targetFile}")
        println("Content:")
        println("------------------------------------------------")
        println(fileDescriptor.targetFile.readText())
        println("------------------------------------------------")
    }

    class TestFieldDescription(
        val name: String,
        val type: String,
        val nullable: Boolean,
    ) {

    }
}
