package ch.senegal.engine.freemarker.templateengine

import freemarker.template.Configuration
import freemarker.template.Template
import freemarker.template.TemplateExceptionHandler
import java.io.File
import java.util.*

class FreemarkerTemplateProcessor(private val templatesClasspathResourceBasePath: String) {
    private val fileEncoding = "UTF-8"
    private val locale: Locale = Locale.GERMANY

    private val cfg: Configuration = createClasspathBasedFreemarkerConfiguration(templatesClasspathResourceBasePath)

    fun processFileContentWithFreemarker(fileDescriptors: List<FreemarkerFileDescriptor>) {
        fileDescriptors.forEach {fileDescriptor -> processFileContentWithFreemarker(fileDescriptor)}
    }

    fun processFileContentWithFreemarker(fileDescriptor: FreemarkerFileDescriptor) {
        val templateClasspathLocation: String = fileDescriptor.templateClassPath
        val targetFile: File = fileDescriptor.targetFile.toFile()
        val model: Map<String, Any?> = fileDescriptor.model
        try {


            if(!targetFile.exists()) {
                // create parent directories if not existing
                targetFile.parentFile.mkdirs()
            }


            val template: Template = cfg.getTemplate(templateClasspathLocation)
            targetFile.writer().use { fileWriter -> template.process(model, fileWriter) }
        } catch (e: Exception) {
            println("Template Error: ${e.message}")
            throw RuntimeException("Exception in freemarker template '$templateClasspathLocation' " +
                    "( in base dir $templatesClasspathResourceBasePath): ${e.message?.replace('\n', 'X')}", e)
        }

    }

    private fun createClasspathBasedFreemarkerConfiguration(templatesClasspathResourceBasePath: String): Configuration {
        val cfg = createBaseConfiguration()
        //allow load templates from classpath
        cfg.setClassLoaderForTemplateLoading(this.javaClass.classLoader, templatesClasspathResourceBasePath)
        return cfg
    }

    private fun createDirectoryBasedFreemarkerConfiguration(baseTemplateDirectory: File): Configuration {
        val cfg = createBaseConfiguration()
        cfg.setDirectoryForTemplateLoading(baseTemplateDirectory);
        return cfg
    }

    private fun createBaseConfiguration(): Configuration {
        // Create your Configuration instance, and specify if up to what FreeMarker
        // version (here 2.3.27) do you want to apply the fixes that are not 100%
        // backward-compatible. See the Configuration JavaDoc for details.
        val cfg = Configuration(Configuration.VERSION_2_3_29)
        // Don't log exceptions inside FreeMarker that it will thrown at you anyway:
        cfg.logTemplateExceptions = false
        cfg.defaultEncoding = fileEncoding
        cfg.locale = locale
        cfg.templateExceptionHandler = TemplateExceptionHandler.RETHROW_HANDLER
        cfg.booleanFormat = "true,false"
        return cfg
    }

}
