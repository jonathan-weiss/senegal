package ch.senegal.engine.freemarker

import ch.senegal.engine.TmpFileUtil
import ch.senegal.engine.template.TemplateTargetWithModel
import ch.senegal.engine.virtualfilesystem.PhysicalFilesVirtualFileSystem
import ch.senegal.plugin.TemplateForFreemarker
import org.junit.jupiter.api.Test
import kotlin.io.path.readText

internal class FreemarkerTemplateProcessorTest {

    @Test
    fun processFileContentWithFreemarker() {
        val virtualFileSystem = PhysicalFilesVirtualFileSystem()
        val templateProcessor = FreemarkerTemplateProcessor("/ch/senegal/engine/freemarker")
        val targetFilePath = TmpFileUtil.createTempFile("freemarker-test")
        val model = mutableMapOf<String, Any>()
        model["packageName"] = "ch.senegal.engine.freemarker"
        model["className"] = "MyPerson"
        model["classFields"] = listOf(
            TestFieldDescription("firstname", "kotlin.String", true),
            TestFieldDescription("lastname", "kotlin.String", false),
        )
        val mySubDelegate = MyDelegate("mySubDelegate", null)
        val myDelegate = MyDelegate("myDelegate", mySubDelegate)

        model["mySpecialDelegate"] = myDelegate
        val fileDescriptor = TemplateTargetWithModel(
            targetFile = targetFilePath,
            model = model,
            template = TemplateForFreemarker("example-template.ftl")
        )
        templateProcessor.processFileContentWithFreemarker(fileDescriptor, virtualFileSystem)
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

    class MyDelegate(private val delegateCode: String,
                     private val subDelegate: MyDelegate?) {

        operator fun get(key: String): Any? {
            println("$delegateCode: $key")
            if(subDelegate != null) {
                return subDelegate
            }
            return "$delegateCode, key:$key"
        }

    }

}
