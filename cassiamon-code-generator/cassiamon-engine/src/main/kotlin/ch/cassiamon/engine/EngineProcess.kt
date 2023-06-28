package ch.cassiamon.engine

import ch.cassiamon.api.registration.DomainUnit
import ch.cassiamon.engine.domain.process.DomainUnitProcessInputDataHelperImpl
import ch.cassiamon.engine.domain.process.DomainUnitProcessTargetFilesDataHelperImpl
import ch.cassiamon.engine.domain.process.TargetFileCollectionProvider
import ch.cassiamon.engine.domain.registration.SchemaProvider
import ch.cassiamon.engine.inputsource.InputSourceDataProvider
import ch.cassiamon.engine.model.ConceptModelGraphCalculator
import kotlin.io.path.absolutePathString

class EngineProcess(private val processSession: ProcessSession) {



    fun runProcess() {
        // gather all concepts, facets, transformer and templateX by the plugin mechanism
        // val registrationApi = RegistrationApiDefaultImpl(processSession)

        processSession.domainUnits.forEach { domainUnit -> processDomainUnit(domainUnit) }

//        // resolve the raw concepts and facets to a resolved schema
//        val templates = registrationApi.provideTemplates()
//        val modelInputData = registrationApi.provideModelInputData()
//
//        println("Schema: $schema")
//        println("Templates: $templates")
//        println("InputData: $modelInputData")
//
//        // traverse whole model and transform (adapt/calculate/transform) the missing model values
//        val conceptModelGraph = ConceptModelGraphCalculator.calculateConceptModelGraph(schema, modelInputData)
//        println("conceptModelGraph: $conceptModelGraph")
//
//        val templateRenderers = templates.map { template -> template.invoke(conceptModelGraph) }.toSet()
//        println("templateRenderers: $templateRenderers")
//
//        templateRenderers.forEach { templateRenderer: TemplateRenderer<*> ->
//            val renderingResults = templateRenderer.renderAll()
//            renderingResults.forEach { renderingResult ->
//                println("File to write: ${renderingResult.targetFile} (${renderingResult.targetFile.absolutePathString()})")
//                processSession.fileSystemAccess.writeFile(renderingResult.targetFile, renderingResult.byteIterator)
//            }
//        }
    }

    private fun processDomainUnit(domainUnit: DomainUnit<*>) {
        val domainUnitInputData = domainUnit.processDomainUnitInputData(processSession.parameterAccess, DomainUnitProcessInputDataHelperImpl(processSession))

        // resolve the raw concepts and facets to a resolved schema
        // TODO These casts are not really a good solution. Move this into the domain unit?
        val schema = (domainUnitInputData as SchemaProvider).provideSchema()
        val modelInputData = (domainUnitInputData as InputSourceDataProvider).provideModelInputData()

        println("Schema: $schema")
        println("InputData: $modelInputData")

        // traverse whole model and transform (adapt/calculate/transform) the missing model values
        val conceptModelGraph = ConceptModelGraphCalculator.calculateConceptModelGraph(schema, modelInputData)
        println("conceptModelGraph: $conceptModelGraph")


        val targetFilesCollector = domainUnit.processDomainUnitTargetFiles(
            processSession.parameterAccess,
            DomainUnitProcessTargetFilesDataHelperImpl(processSession, schema, conceptModelGraph)
        )

        // TODO This casts are not really a good solution. Move this into the domain unit?
        val targetFilesWithContent = (targetFilesCollector as TargetFileCollectionProvider).getTargetFiles()


        println("targetFiles: $targetFilesWithContent")


        targetFilesWithContent.forEach { targetFileWithContent ->
            println("File to write: ${targetFileWithContent.targetFile} (${targetFileWithContent.targetFile.absolutePathString()})")
            processSession.fileSystemAccess.writeFile(targetFileWithContent.targetFile, targetFileWithContent.fileContent)
        }


    }
}
