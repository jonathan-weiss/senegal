package ch.senegal.engine.process

import ch.senegal.engine.calculation.ModelTreeCalculations
import ch.senegal.engine.freemarker.FreemarkerTemplateProcessor
import ch.senegal.engine.logger.SenegalLogger
import ch.senegal.engine.template.TemplateTargetWithModelCreator
import ch.senegal.engine.model.MutableModelTree
import ch.senegal.engine.plugin.finder.PluginFinder
import ch.senegal.engine.plugin.resolver.PluginResolver
import ch.senegal.engine.parameters.ParameterReader
import ch.senegal.engine.parameters.ParameterSource
import ch.senegal.engine.parameters.PathConfigParameterName
import ch.senegal.engine.virtualfilesystem.VirtualFileSystem
import ch.senegal.engine.xml.XmlFileParser
import ch.senegal.engine.xml.schemacreator.XmlSchemaInitializer
import ch.senegal.plugin.TemplateForFreemarker

class SenegalProcess(
    private val pluginFinder: PluginFinder,
    private val virtualFileSystem: VirtualFileSystem,
    private val parameterSources: List<ParameterSource>
) {

    private val logger = SenegalLogger(virtualFileSystem)

    fun runSenegalEngine() {
        val parameterReader = ParameterReader(parameterSources)

        val foundPlugins = pluginFinder.findAllPlugins()
        val resolvedPlugins = PluginResolver.resolvePlugins(foundPlugins)

        logger.logUserInfo("Parameters:")
        logger.logUserInfo("------------")
        parameterReader.getParameterList().forEach { logger.logUserInfo(it) }
        logger.logUserInfo("------------")

        val placeholders = parameterReader.getPlaceholders()
        logger.logUserInfo("Placeholders:")
        logger.logUserInfo("------------")
        placeholders.forEach { (key, value) -> logger.logUserInfo("$key=$value") }
        logger.logUserInfo("------------")

        val definitionDirectory = parameterReader.getParameter(PathConfigParameterName.DefinitionDirectory)
        val defaultOutputDirectory = parameterReader.getParameter(PathConfigParameterName.DefaultOutputDirectory)
        val schemaDirectory = XmlSchemaInitializer.createSchemaDirectory(definitionDirectory, virtualFileSystem)

        XmlSchemaInitializer.initializeXmlSchemaFile(schemaDirectory, resolvedPlugins, virtualFileSystem)

        val xmlDefinitionFile = parameterReader.getParameter(PathConfigParameterName.XmlDefinitionFile)

        val modelTree: MutableModelTree = XmlFileParser.validateAndReadXmlFile(resolvedPlugins, xmlDefinitionFile, placeholders, virtualFileSystem, logger)

        ModelTreeCalculations.executeCalculations(modelTree)

        val templateTargetsWithModel = TemplateTargetWithModelCreator.createTemplateTargets(modelTree, defaultOutputDirectory)

        logger.logDebug("Generated files:")
        logger.logDebug("------------")
        templateTargetsWithModel.forEach {
            logger.logDebug("${it.targetFile} (with template ${it.template})")
        }
        logger.logDebug("------------")

        val freemarkerTemplateProcessor = FreemarkerTemplateProcessor("")
        templateTargetsWithModel.forEach { templateTargetWithModel ->
            when(templateTargetWithModel.template) {
                is TemplateForFreemarker -> freemarkerTemplateProcessor.processFileContentWithFreemarker(templateTargetWithModel, virtualFileSystem)
                else -> throw IllegalStateException("Template type is not supported: ${templateTargetWithModel.template}")
            }
        }

        virtualFileSystem.close()
    }
}
