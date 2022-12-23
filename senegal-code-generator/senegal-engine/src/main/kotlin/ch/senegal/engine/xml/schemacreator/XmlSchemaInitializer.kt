package ch.senegal.engine.xml.schemacreator

import ch.senegal.engine.plugin.resolver.ResolvedPlugins
import ch.senegal.engine.virtualfilesystem.VirtualFileSystem
import java.nio.file.Path

object XmlSchemaInitializer {

    fun createSchemaDirectory(definitionDirectory: Path, virtualFileSystem: VirtualFileSystem): Path {
        val schemaDirectory = definitionDirectory.resolve("schema")
        virtualFileSystem.createDirectory(schemaDirectory)
        return schemaDirectory
    }

    fun initializeXmlSchemaFile(
        schemaDirectory: Path,
        resolvedPlugins: ResolvedPlugins,
        virtualFileSystem: VirtualFileSystem
    ) {
        val xmlSchemaFileContent = XmlDomSchemaCreator.createPluginSchema(resolvedPlugins)
        val xmlSchemaFileName = "senegal-schema.xsd"
        virtualFileSystem.writeFile(schemaDirectory.resolve(xmlSchemaFileName), xmlSchemaFileContent)
    }
}
