package org.codeblessing.senegal.codegen

import org.codeblessing.senegal.codegen.schema.EntitiesSchema
import org.codeblessing.sourceamazing.schema.api.SchemaApi
import org.codeblessing.sourceamazing.xmlschema.api.XmlSchemaApi
import java.nio.file.Paths

fun main() {
    println("Hello World of Code Generation!")

    val entitySchema = SchemaApi.withSchema(EntitiesSchema::class) { schemaContext ->
        XmlSchemaApi.createXsdSchemaAndReadXmlFile(schemaContext, CodeGenerationParameters.xmlDefinitionFile())
    }


}