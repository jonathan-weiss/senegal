package ch.cassiamon.engine

import ch.cassiamon.engine.schema.finder.RegistrarFinder
import ch.cassiamon.engine.schema.registration.RegistrationApiDefaultImpl
import ch.cassiamon.engine.schema.registration.SchemaRegistrationDefaultImpl
import ch.cassiamon.engine.schema.registration.TemplateNodesProviderDefaultImpl


fun main() {
    // val registrars = RegistrarFinder.findAllRegistrars()
    val registrars = listOf(ExampleInternalRegistrar())
    println("Registrars: [${registrars.joinToString { it.projectName.name }}]")

    val registrationApi = RegistrationApiDefaultImpl()
    registrars.forEach { it.configure(registrationApi) }

    val schema = registrationApi.provideSchema()
    val templates = registrationApi.provideTemplates()

    println("Schema: $schema")
    println("Templates: $templates")

    val templateNodesProvider = TemplateNodesProviderDefaultImpl()

    val templateRenderers = templates.map { template -> template.invoke(templateNodesProvider) }.toSet()

    println("TemplateRenderer: $templateRenderers")

    templateRenderers.forEach { templateRenderer ->
        templateRenderer.targetFilesWithModel.forEach { file ->
            println("File to render: ${file.targetFile}")
            println("Model for rendering: ${file.model}")
            val byteIterator = templateRenderer.templateRenderer(file)
            println("ByteIterator from templateRenderer: $byteIterator")
        }
    }



}
