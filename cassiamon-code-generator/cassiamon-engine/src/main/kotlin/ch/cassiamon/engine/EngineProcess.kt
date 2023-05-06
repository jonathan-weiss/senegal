package ch.cassiamon.engine

import ch.cassiamon.engine.model.ConceptModelGraphCalculator
import ch.cassiamon.engine.schema.registration.RegistrationApiDefaultImpl
import ch.cassiamon.pluginapi.template.TemplateRenderer
import kotlin.io.path.absolutePathString

class EngineProcess(private val processSession: ProcessSession) {



    fun runProcess() {
        // gather all concepts, facets, transformer and templateX by the plugin mechanism
        val registrationApi = RegistrationApiDefaultImpl(processSession)
        processSession.registrars.forEach { it.configure(registrationApi) }

        // resolve the raw concepts and facets to a resolved schema
        val schema = registrationApi.provideSchema()
        val templates = registrationApi.provideTemplates()
        val modelInputData = registrationApi.provideModelInputData()

        println("Schema: $schema")
        println("Templates: $templates")
        println("InputData: $modelInputData")

        // traverse whole model and transform (adapt/calculate/transform) the missing model values
        val conceptModelGraph = ConceptModelGraphCalculator.calculateConceptModelGraph(schema, modelInputData)
        println("conceptModelGraph: $conceptModelGraph")

        val templateRenderers = templates.map { template -> template.invoke(conceptModelGraph) }.toSet()
        println("templateRenderers: $templateRenderers")

        templateRenderers.forEach { templateRenderer: TemplateRenderer ->
            templateRenderer.targetFilesWithModel.forEach { targetGeneratedFileWithModel ->
                val byteIterator = templateRenderer.templateRendererFunction(targetGeneratedFileWithModel)
                println("File to write: ${targetGeneratedFileWithModel.targetFile} (${targetGeneratedFileWithModel.targetFile.absolutePathString()})")
                processSession.fileSystemAccess.writeFile(targetGeneratedFileWithModel.targetFile, byteIterator)
            }
        }


    }
}
