package ch.cassiamon.templates.freemarker

import ch.cassiamon.pluginapi.filesystem.FileSystemAccess
import ch.cassiamon.pluginapi.logger.LoggerFacade
import ch.cassiamon.pluginapi.parameter.ParameterAccess
import ch.cassiamon.pluginapi.registration.InputSourceDataCollector
import ch.cassiamon.pluginapi.schema.SchemaAccess
import ch.cassiamon.pluginapi.template.TargetGeneratedFileWithModel
import ch.cassiamon.pluginapi.template.TemplateRenderer
import ch.cassiamon.pluginapi.template.helper.StringContentByteIterator
import ch.cassiamon.templates.freemarker.writer.FreemarkerTemplateProcessor
import java.nio.file.Path

object FreemarkerTemplateFactory {

    private val templateEngine: FreemarkerTemplateProcessor = FreemarkerTemplateProcessor("")

    fun createTemplateRenderer(
        targetFiles: Set<TargetGeneratedFileWithModel>,
        freemarkerTemplateClasspath: String,
        fileSystemAccess: FileSystemAccess,
        logger: LoggerFacade,
        receiveParameterAccess: ParameterAccess,
        ): TemplateRenderer {

        return TemplateRenderer(targetFiles) { targetGeneratedFileWithModel: TargetGeneratedFileWithModel ->
            return@TemplateRenderer templateEngine.processWithFreemarker(targetGeneratedFileWithModel, freemarkerTemplateClasspath)
        }

    }
}
