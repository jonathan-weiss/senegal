package ch.senegal.engine.process

import ch.senegal.engine.freemarker.templateengine.FreemarkerFileDescriptor
import ch.senegal.engine.freemarker.templateengine.FreemarkerTemplateProcessor
import ch.senegal.engine.freemarker.templatemodel.TemplateModelCreator
import ch.senegal.engine.model.ModelTree
import ch.senegal.engine.plugin.finder.PluginFinder
import ch.senegal.engine.plugin.resolver.PluginResolver
import ch.senegal.engine.properties.ParameterReader
import ch.senegal.engine.properties.PathParameterName
import ch.senegal.engine.util.FileUtil
import ch.senegal.engine.xml.XmlFileParser
import ch.senegal.engine.xml.schemacreator.XmlSchemaInitializer

object SenegalProcess {

    fun runSenegalEngine() {
        val foundPlugins = PluginFinder.findAllPlugins()
        val resolvedPlugins = PluginResolver.resolvePlugins(foundPlugins)

        val definitionDirectory = ParameterReader.getParameter(PathParameterName.DefinitionDirectory)
        val schemaDirectory = XmlSchemaInitializer.createSchemaDirectory(definitionDirectory)

        XmlSchemaInitializer.initializeXmlSchemaFile(schemaDirectory, resolvedPlugins)

        val xmlDefinitionFile = ParameterReader.getParameter(PathParameterName.XmlDefinitionFile)
        FileUtil.checkFileReadable(xmlDefinitionFile)

        val modelTree: ModelTree = XmlFileParser.validateAndReadXmlFile(resolvedPlugins, xmlDefinitionFile)

        val freemarkerFileDescriptors = TemplateModelCreator.createTemplateTargets(modelTree, resolvedPlugins)

        val templateProcessor = FreemarkerTemplateProcessor("")

        templateProcessor.processFileContentWithFreemarker(freemarkerFileDescriptors)
    }
}
