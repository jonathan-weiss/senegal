package ch.cassiamon.templates.freemarker

import ch.cassiamon.api.extensions.ClasspathLocation
import ch.cassiamon.api.extensions.ExtensionName
import ch.cassiamon.api.extensions.template.ClasspathTemplateExtension
import ch.cassiamon.api.logger.LoggerFacade
import ch.cassiamon.api.model.ConceptModelNode
import ch.cassiamon.api.parameter.ParameterAccess
import ch.cassiamon.api.template.TargetGeneratedFileWithModel
import ch.cassiamon.api.template.TemplateRenderer
import ch.cassiamon.templates.freemarker.writer.FreemarkerTemplateProcessor

class FreemarkerClasspathTemplateExtension: ClasspathTemplateExtension {

    companion object {
        val extensionName = ExtensionName.of("FreemarkerTemplates")
    }

    override fun getExtensionName(): ExtensionName {
        return extensionName
    }

    override fun initializeExtension(loggerFacade: LoggerFacade, parameterAccess: ParameterAccess) {
        // nothing to do
    }

    private val templateEngine: FreemarkerTemplateProcessor = FreemarkerTemplateProcessor("")
    override fun fillTemplate(
        targetFilesWithModel: Set<TargetGeneratedFileWithModel<ConceptModelNode>>,
        templateClasspathLocation: ClasspathLocation
    ): TemplateRenderer<ConceptModelNode> {

        return TemplateRenderer(targetFilesWithModel) { targetGeneratedFileWithModel: TargetGeneratedFileWithModel<ConceptModelNode> ->
            return@TemplateRenderer templateEngine.processWithFreemarker(targetGeneratedFileWithModel, templateClasspathLocation.classpath)
        }

    }
}
