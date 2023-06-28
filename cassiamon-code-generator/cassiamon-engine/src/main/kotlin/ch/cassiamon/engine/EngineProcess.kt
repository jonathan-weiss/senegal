package ch.cassiamon.engine

import ch.cassiamon.api.registration.DomainUnit
import ch.cassiamon.engine.domain.process.DomainUnitProcessInputDataHelperImpl
import ch.cassiamon.engine.domain.process.DomainUnitProcessTargetFilesDataHelperImpl
import ch.cassiamon.engine.domain.registration.SchemaProvider
import ch.cassiamon.engine.inputsource.InputSourceDataProvider
import ch.cassiamon.engine.model.ConceptModelGraphCalculator

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
        // TODO here, we can create the ConceptModelGraph

        // resolve the raw concepts and facets to a resolved schema
        val schema = (domainUnitInputData as SchemaProvider).provideSchema()
        val modelInputData = (domainUnitInputData as InputSourceDataProvider).provideModelInputData()

        println("Schema: $schema")
        println("InputData: $modelInputData")

        // traverse whole model and transform (adapt/calculate/transform) the missing model values
        val conceptModelGraph = ConceptModelGraphCalculator.calculateConceptModelGraph(schema, modelInputData)
        println("conceptModelGraph: $conceptModelGraph")


        domainUnit.processDomainUnitTargetFiles(processSession.parameterAccess, DomainUnitProcessTargetFilesDataHelperImpl(processSession, schema, conceptModelGraph))

        println(processSession.targetFilesCollector)

    }
}
