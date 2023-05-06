package ch.cassiamon.api.extensions.template

import ch.cassiamon.api.extensions.ClasspathLocation
import ch.cassiamon.api.extensions.Extension
import ch.cassiamon.api.extensions.ExtensionInitialization
import ch.cassiamon.api.template.TargetGeneratedFileWithModel
import ch.cassiamon.api.template.TemplateRenderer

interface ClasspathTemplateExtension: Extension, ExtensionInitialization {

    fun fillTemplate(targetFilesWithModel: Set<TargetGeneratedFileWithModel>, templateClasspathLocation: ClasspathLocation): TemplateRenderer
}
