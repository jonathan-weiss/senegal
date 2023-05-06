package ch.cassiamon.api.registration

import ch.cassiamon.api.extensions.ClasspathLocation
import ch.cassiamon.api.extensions.ExtensionName
import ch.cassiamon.api.template.TargetGeneratedFileWithModel
import ch.cassiamon.api.template.TemplateRenderer

typealias TemplatesRegistrationApi = (TemplatesRegistration.() -> Unit) -> Unit
interface TemplatesRegistration {

    fun newTemplate(templateFunction: TemplateFunction)

    fun newTemplateRendererWithClasspathTemplateExtension(extensionName: ExtensionName, targetFilesWithModel: Set<TargetGeneratedFileWithModel>, templateClasspath: ClasspathLocation): TemplateRenderer

    fun newTemplateRenderer(targetFilesWithModel: Set<TargetGeneratedFileWithModel>, templateRendererFunction: (targetGeneratedFileWithModel: TargetGeneratedFileWithModel) -> ByteIterator): TemplateRenderer

}
