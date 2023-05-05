package ch.cassiamon.templates.freemarker.writer

import ch.cassiamon.pluginapi.template.TargetGeneratedFileWithModel
import freemarker.template.Configuration
import freemarker.template.Template
import freemarker.template.TemplateExceptionHandler
import java.io.CharArrayWriter
import java.util.*

class FreemarkerTemplateProcessor(private val templatesClasspathResourceBasePath: String) {
    companion object {
        const val rootNodeKey = "rootTemplateModels"
    }

    private val fileEncoding = "UTF-8"
    private val locale: Locale = Locale.GERMANY

    private val cfg: Configuration = createClasspathBasedFreemarkerConfiguration(templatesClasspathResourceBasePath)

    fun processWithFreemarker(targetGeneratedFileWithModel: TargetGeneratedFileWithModel, templateClasspathLocation: String): ByteIterator {
        val model: Map<String, Any?> = mapOf(rootNodeKey to targetGeneratedFileWithModel.model)
        try {
            val template: Template = cfg.getTemplate(templateClasspathLocation)
            val writer = CharArrayWriter()
            return writer.use {
                template.process(model, writer)
                return@use transformToByteArray(writer.toCharArray())

            }
        } catch (e: Exception) {
            throw RuntimeException("Exception in freemarker template '$templateClasspathLocation' " +
                    "( in base dir $templatesClasspathResourceBasePath): ${e.message?.replace('\n', 'X')}", e)
        }
    }

    private fun transformToByteArray(charArray: CharArray): ByteIterator {
        return charArray.concatToString().toByteArray(Charsets.UTF_8).iterator()
    }

    private fun createClasspathBasedFreemarkerConfiguration(templatesClasspathResourceBasePath: String): Configuration {
        val cfg = createBaseConfiguration()
        //allow load templates from classpath
        cfg.setClassLoaderForTemplateLoading(this.javaClass.classLoader, templatesClasspathResourceBasePath)
        return cfg
    }

    private fun createBaseConfiguration(): Configuration {
        // Create your Configuration instance, and specify if up to what FreeMarker
        // version (here 2.3.27) do you want to apply the fixes that are not 100%
        // backward-compatible. See the Configuration JavaDoc for details.
        val cfg = Configuration(Configuration.VERSION_2_3_29)
        // Don't log exceptions inside FreeMarker that it will throw at you anyway:
        cfg.logTemplateExceptions = false
        cfg.defaultEncoding = fileEncoding
        cfg.locale = locale
        cfg.templateExceptionHandler = TemplateExceptionHandler.RETHROW_HANDLER
        cfg.booleanFormat = "true,false"
        return cfg
    }

}
