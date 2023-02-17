package ch.cassiamon.engine

import ch.cassiamon.engine.model.graph.ModelGraphCreator
import ch.cassiamon.engine.schema.registration.RegistrationApiDefaultImpl
import ch.cassiamon.engine.template.TemplateNodesProviderDefaultImpl
import org.junit.jupiter.api.Test
import java.nio.charset.Charset

class CassiamonProcessTest {

    @Test
    fun createModelGraph() {
        val registrars = listOf(TestRegistrar())
        println("Registrars: [${registrars.joinToString { it.projectName.name }}]")

        val registrationApi = RegistrationApiDefaultImpl()
        registrars.forEach { it.configure(registrationApi) }

        val schema = registrationApi.provideSchema()
        val templates = registrationApi.provideTemplates()

        println("Schema: $schema")
        println("Templates: $templates")

        val modelInputData = TestFixtures.createModelInputData()
        val modelGraph = ModelGraphCreator.calculateGraph(schema, modelInputData)

        val templateNodesProvider = TemplateNodesProviderDefaultImpl(modelGraph)

        val templateRenderers = templates.map { template -> template.invoke(templateNodesProvider) }.toSet()

        println("TemplateRenderer: $templateRenderers")

        templateRenderers.forEach { templateRenderer ->
            templateRenderer.targetFilesWithModel.forEach { file ->
                println("File to render: ${file.targetFile}")
                println("Model for rendering: ${file.model}")
                val byteIterator = byteIteratorAsString(templateRenderer.templateRenderer(file))
                println("ByteIterator from templateRenderer: \n$byteIterator")
            }
        }

    }

    private fun byteIteratorAsString(byteIterator: ByteIterator): String {
        val byteList : MutableList<Byte> = mutableListOf()
        byteIterator.forEach { byte: Byte -> byteList.add(byte) }
        return byteList.toByteArray().toString(Charset.defaultCharset())
    }
}
