package ch.cassiamon.xml.schemagic.schemacreator

import ch.cassiamon.pluginapi.filesystem.FileSystemAccess
import ch.cassiamon.pluginapi.schema.SchemaAccess
import java.nio.file.Path

object XmlSchemaInitializer {

    private val schemaXmlContent: String = """
        <?xml version="1.0" encoding="UTF-8" standalone="no"?>
        <xsd:schema xmlns="https://cassiamon.ch/cassiamon-schemagic" elementFormDefault="qualified" targetNamespace="https://cassiamon.ch/cassiamon-schemagic" xmlns:xsd="http://www.w3.org/2001/XMLSchema">
            <xsd:element name="cassiamon">
                <xsd:complexType>
                    <xsd:sequence maxOccurs="unbounded" minOccurs="0">
                         <xsd:any maxOccurs="unbounded" minOccurs="0"/>
                    </xsd:sequence>
                </xsd:complexType>
            </xsd:element>
        </xsd:schema>
    """.trimIndent()

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
        val xmlSchemaFileContent = schemaXmlContent // TODO Call to XmlDomSchemaCreator.createPluginSchema(resolvedPlugins)
        val xmlSchemaFileName = "cassiamon-schemagic-schema.xsd"
        fileSystemAccess.writeFile(schemaDirectory.resolve(xmlSchemaFileName), xmlSchemaFileContent)
    }
}
