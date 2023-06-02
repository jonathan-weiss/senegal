package ch.cassiamon.engine.domain.registration

import ch.cassiamon.engine.extension.ExtensionAccess
import ch.cassiamon.api.extensions.ClasspathLocation
import ch.cassiamon.api.extensions.ExtensionName
import ch.cassiamon.api.model.ConceptModelNode
import ch.cassiamon.api.registration.*
import ch.cassiamon.api.template.TargetGeneratedFileWithModel
import ch.cassiamon.api.template.TemplateRenderer

class TemplateRegistrationDefaultImpl(
    private val extensionAccess: ExtensionAccess
    ): TemplatesRegistration, TemplateProvider {
    private val templateFunctions: MutableList<TemplateFunction<*>> = mutableListOf()

    override fun <T> newTemplate(templateFunction: TemplateFunction<T>) {
        templateFunctions.add(templateFunction)
    }

    override fun newTemplateRendererWithClasspathTemplateExtension(
        extensionName: ExtensionName,
        targetFilesWithModel: Set<TargetGeneratedFileWithModel<ConceptModelNode>>,
        templateClasspath: ClasspathLocation
    ): TemplateRenderer<ConceptModelNode> {
        return extensionAccess.getClasspathTemplateExtension(extensionName).fillTemplate(targetFilesWithModel, templateClasspath)
    }

    override fun <T> newTemplateRenderer(
        targetFilesWithModel: Set<TargetGeneratedFileWithModel<T>>,
        templateRendererFunction: (targetGeneratedFileWithModel: TargetGeneratedFileWithModel<T>) -> ByteIterator
    ): TemplateRenderer<T> {
        return TemplateRenderer(targetFilesWithModel, templateRendererFunction)
    }

    override fun provideTemplates(): List<TemplateFunction<*>> {
        return templateFunctions
    }
}
