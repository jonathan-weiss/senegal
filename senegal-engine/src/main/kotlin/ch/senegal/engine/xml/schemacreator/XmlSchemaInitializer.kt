package ch.senegal.engine.xml.schemacreator

import ch.senegal.engine.plugin.finder.PluginFinder
import ch.senegal.engine.plugin.tree.PluginTreeCreator
import ch.senegal.engine.properties.ArgumentReader

object XmlSchemaInitializer {

    fun initializeXmlSchemas() {
        val definitionDirectory = ArgumentReader.getDefinitionDirectory()
        val schemaDirectory = definitionDirectory.resolve("schema")
        schemaDirectory.toFile().mkdirs()

        val pluginTree = PluginTreeCreator.createPluginTree(PluginFinder.findAllPlugins())

        val xmlSchemaFileContent = XmlSchemaCreator.createPluginTreeSchema(pluginTree)
        val xmlSchemaFileName = "senegal-schema.xsd"
        FileWriter.writeFile(schemaDirectory.resolve(xmlSchemaFileName), xmlSchemaFileContent)


    }
}
