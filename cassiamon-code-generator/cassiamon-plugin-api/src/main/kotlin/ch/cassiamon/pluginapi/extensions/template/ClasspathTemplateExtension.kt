package ch.cassiamon.pluginapi.extensions.template

import ch.cassiamon.pluginapi.extensions.ClasspathLocation
import ch.cassiamon.pluginapi.extensions.Extension
import ch.cassiamon.pluginapi.extensions.ExtensionInitialization
import ch.cassiamon.pluginapi.template.TargetGeneratedFileWithModel
import ch.cassiamon.pluginapi.template.TemplateRenderer

interface ClasspathTemplateExtension: Extension, ExtensionInitialization {

    fun fillTemplate(targetFilesWithModel: Set<TargetGeneratedFileWithModel>, templateClasspathLocation: ClasspathLocation): TemplateRenderer
}
