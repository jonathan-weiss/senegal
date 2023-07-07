package ch.cassiamon.xml.schemagic.schemacreator

import ch.cassiamon.api.filesystem.FileSystemAccess
import ch.cassiamon.api.process.schema.SchemaAccess
import java.nio.file.Path

object XmlSchemaInitializer {

    fun createSchemaDirectory(definitionDirectory: Path, fileSystemAccess: FileSystemAccess): Path {
        val schemaDirectory = definitionDirectory.resolve("schema")
        fileSystemAccess.createDirectory(schemaDirectory)
        return schemaDirectory
    }

    fun initializeXmlSchemaFile(
        schemaDirectory: Path,
        schema: SchemaAccess,
        fileSystemAccess: FileSystemAccess
    ) {
        val xmlSchemaFileContent = XmlDomSchemaCreator.createSchemagicSchemaContent(schema)
        val xmlSchemaFileName = "cassiamon-schemagic-schema.xsd"
        fileSystemAccess.writeFile(schemaDirectory.resolve(xmlSchemaFileName), xmlSchemaFileContent)
    }
}
