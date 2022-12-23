package ch.senegal.engine.process

import ch.senegal.engine.calculation.ModelTreeCalculations
import ch.senegal.engine.freemarker.templateengine.FreemarkerTemplateProcessor
import ch.senegal.engine.freemarker.templatemodel.TemplateFileDescriptionCreator
import ch.senegal.engine.model.MutableModelTree
import ch.senegal.engine.plugin.finder.PluginFinder
import ch.senegal.engine.plugin.resolver.PluginResolver
import ch.senegal.engine.properties.ParameterReader
import ch.senegal.engine.properties.PathParameterName
import ch.senegal.engine.virtualfilesystem.PhysicalFilesVirtualFileSystem
import ch.senegal.engine.virtualfilesystem.VirtualFileSystem
import ch.senegal.engine.xml.XmlFileParser
import ch.senegal.engine.xml.schemacreator.XmlSchemaInitializer

object SenegalProcess {

    fun runSenegalEngine() {
        val virtualFileSystem: VirtualFileSystem = PhysicalFilesVirtualFileSystem()
        val parameterReader = ParameterReader(virtualFileSystem)

        val foundPlugins = PluginFinder.findAllPlugins()
        val resolvedPlugins = PluginResolver.resolvePlugins(foundPlugins)

        println("Parameters:")
        println("------------")
        parameterReader.getParameterList().forEach { println(it) }
        println("------------")

        val placeholders = parameterReader.getPlaceholders()
        println("Placeholders:")
        println("------------")
        placeholders.forEach { (key, value) -> println("$key=$value") }
        println("------------")

        val definitionDirectory = parameterReader.getParameter(PathParameterName.DefinitionDirectory)
        val defaultOutputDirectory = parameterReader.getParameter(PathParameterName.DefaultOutputDirectory)
        val schemaDirectory = XmlSchemaInitializer.createSchemaDirectory(definitionDirectory, virtualFileSystem)

        XmlSchemaInitializer.initializeXmlSchemaFile(schemaDirectory, resolvedPlugins, virtualFileSystem)

        val xmlDefinitionFile = parameterReader.getParameter(PathParameterName.XmlDefinitionFile)

        val modelTree: MutableModelTree = XmlFileParser.validateAndReadXmlFile(resolvedPlugins, xmlDefinitionFile, placeholders, virtualFileSystem)

        ModelTreeCalculations.executeCalculations(modelTree, resolvedPlugins)

        val freemarkerFileDescriptors = TemplateFileDescriptionCreator.createTemplateTargets(modelTree, resolvedPlugins, defaultOutputDirectory)

        val templateProcessor = FreemarkerTemplateProcessor("")

        println("Generated files:")
        println("------------")
        freemarkerFileDescriptors.forEach {
            println("${it.targetFile} (with template ${it.templateClassPath})")
        }
        println("------------")

        templateProcessor.processFileContentWithFreemarker(freemarkerFileDescriptors, virtualFileSystem)
    }
}
