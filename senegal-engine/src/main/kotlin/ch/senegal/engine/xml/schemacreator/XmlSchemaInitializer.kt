package ch.senegal.engine.xml.schemacreator

import ch.senegal.engine.plugin.finder.PluginFinder
import ch.senegal.engine.plugin.resolver.PluginResolver
import ch.senegal.engine.properties.ArgumentReader

object XmlSchemaInitializer {

    fun initializeXmlSchemas() {
        val definitionDirectory = ArgumentReader.getDefinitionDirectory()
        val schemaDirectory = definitionDirectory.resolve("schema")
        schemaDirectory.toFile().mkdirs()

        val resolvedPlugins = PluginResolver.resolvePlugins(PluginFinder.findAllPlugins())

        val xmlSchemaFileContent = XmlDomSchemaCreator.createPluginSchema(resolvedPlugins)
        val xmlSchemaFileName = "senegal-schema.xsd"
        FileWriter.writeFile(schemaDirectory.resolve(xmlSchemaFileName), xmlSchemaFileContent)


    }
}
