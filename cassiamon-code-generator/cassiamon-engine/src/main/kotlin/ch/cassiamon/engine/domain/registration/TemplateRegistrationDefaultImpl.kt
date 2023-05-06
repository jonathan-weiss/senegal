package ch.cassiamon.engine.domain.registration

import ch.cassiamon.engine.extension.ExtensionAccess
import ch.cassiamon.pluginapi.extensions.ClasspathLocation
import ch.cassiamon.pluginapi.extensions.ExtensionName
import ch.cassiamon.pluginapi.registration.*
import ch.cassiamon.pluginapi.template.TargetGeneratedFileWithModel
import ch.cassiamon.pluginapi.template.TemplateRenderer

class TemplateRegistrationDefaultImpl(
    private val extensionAccess: ExtensionAccess
    ): TemplatesRegistration, TemplateProvider {
    private val templateFunctions: MutableList<TemplateFunction> = mutableListOf()

    override fun newTemplate(templateFunction: TemplateFunction) {
        templateFunctions.add(templateFunction)
    }

    override fun newTemplateRendererWithClasspathTemplateExtension(
        extensionName: ExtensionName,
        targetFilesWithModel: Set<TargetGeneratedFileWithModel>,
        templateClasspath: ClasspathLocation
    ): TemplateRenderer {
        return extensionAccess.getClasspathTemplateExtension(extensionName).fillTemplate(targetFilesWithModel, templateClasspath)
    }

    override fun newTemplateRenderer(
        targetFilesWithModel: Set<TargetGeneratedFileWithModel>,
        templateRendererFunction: (targetGeneratedFileWithModel: TargetGeneratedFileWithModel) -> ByteIterator
    ): TemplateRenderer {
        return TemplateRenderer(targetFilesWithModel, templateRendererFunction)
    }

    override fun provideTemplates(): List<TemplateFunction> {
        return templateFunctions
    }
}
