package ch.cassiamon.api.registration

import ch.cassiamon.api.extensions.ClasspathLocation
import ch.cassiamon.api.extensions.ExtensionName
import ch.cassiamon.api.model.ConceptModelNode
import ch.cassiamon.api.template.TargetGeneratedFileWithModel
import ch.cassiamon.api.template.TemplateRenderer

typealias TemplatesRegistrationApi = (TemplatesRegistration.() -> Unit) -> Unit
interface TemplatesRegistration {

    fun <T> newTemplate(templateFunction: TemplateFunction<T>)

    fun newTemplateRendererWithClasspathTemplateExtension(extensionName: ExtensionName, targetFilesWithModel: Set<TargetGeneratedFileWithModel<ConceptModelNode>>, templateClasspath: ClasspathLocation): TemplateRenderer<ConceptModelNode>

    fun <T> newTemplateRenderer(targetFilesWithModel: Set<TargetGeneratedFileWithModel<T>>, templateRendererFunction: (targetGeneratedFileWithModel: TargetGeneratedFileWithModel<T>) -> ByteIterator): TemplateRenderer<T>

}
