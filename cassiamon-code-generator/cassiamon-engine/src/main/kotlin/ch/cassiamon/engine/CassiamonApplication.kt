package ch.cassiamon.engine

import ch.cassiamon.engine.model.graph.ModelGraph
import ch.cassiamon.engine.schema.registration.RegistrationApiDefaultImpl
import ch.cassiamon.engine.template.TemplateNodesProviderDefaultImpl


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

    val modelGraph = ModelGraph(emptyMap())
    val templateNodesProvider = TemplateNodesProviderDefaultImpl(modelGraph)

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
