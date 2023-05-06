package ch.cassiamon.pluginapi.registration

import ch.cassiamon.pluginapi.extensions.ClasspathLocation
import ch.cassiamon.pluginapi.extensions.ExtensionName
import ch.cassiamon.pluginapi.template.TargetGeneratedFileWithModel
import ch.cassiamon.pluginapi.template.TemplateRenderer


interface TemplatesRegistration {

    fun newTemplate(templateFunction: TemplateFunction)

    fun newTemplateRendererWithClasspathTemplateExtension(extensionName: ExtensionName, targetFilesWithModel: Set<TargetGeneratedFileWithModel>, templateClasspath: ClasspathLocation): TemplateRenderer

    fun newTemplateRenderer(targetFilesWithModel: Set<TargetGeneratedFileWithModel>, templateRendererFunction: (targetGeneratedFileWithModel: TargetGeneratedFileWithModel) -> ByteIterator): TemplateRenderer

}
