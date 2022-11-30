package ch.senegal.engine.xml.schemacreator

import ch.senegal.engine.plugin.finder.PluginFinder
import ch.senegal.engine.properties.ArgumentReader

object XmlSchemaInitializer {

    fun initializeXmlSchemas() {
        val definitionDirectory = ArgumentReader.getDefinitionDirectory()
        val schemaDirectory = definitionDirectory.resolve("schema")
        schemaDirectory.toFile().mkdirs()

        val concepts = PluginFinder.findAllConceptPlugins()
        println("Found concepts: $concepts")
        concepts.forEach { concept ->
            val xmlSchemaFileContent = XmlSchemaCreator.createConceptSchema(concept)
            val xmlSchemaFileName = concept.conceptName.name.lowercase() + ".xsd"
            FileWriter.writeFile(schemaDirectory.resolve(xmlSchemaFileName), xmlSchemaFileContent)
        }


    }
}
