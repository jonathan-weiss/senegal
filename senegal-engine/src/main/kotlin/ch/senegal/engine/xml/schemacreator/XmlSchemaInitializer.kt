package ch.senegal.engine.xml.schemacreator

import ch.senegal.engine.plugin.resolver.ResolvedPlugins
import java.nio.file.Path

object XmlSchemaInitializer {

    fun createSchemaDirectory(definitionDirectory: Path): Path {
        val schemaDirectory = definitionDirectory.resolve("schema")
        schemaDirectory.toFile().mkdirs()
        return schemaDirectory
    }

    fun initializeXmlSchemaFile(schemaDirectory: Path, resolvedPlugins: ResolvedPlugins) {
        val xmlSchemaFileContent = XmlDomSchemaCreator.createPluginSchema(resolvedPlugins)
        val xmlSchemaFileName = "senegal-schema.xsd"
        FileWriter.writeFile(schemaDirectory.resolve(xmlSchemaFileName), xmlSchemaFileContent)
    }
}
