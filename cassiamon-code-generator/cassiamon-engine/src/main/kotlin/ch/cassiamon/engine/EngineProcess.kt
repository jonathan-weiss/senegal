package ch.cassiamon.engine

import ch.cassiamon.api.registration.DomainUnit
import ch.cassiamon.engine.domain.process.*
import kotlin.io.path.absolutePathString

class EngineProcess(private val processSession: ProcessSession) {



    fun runProcess() {
        processSession.domainUnits.forEach { domainUnit -> processDomainUnit(domainUnit) }
    }

    private fun processDomainUnit(domainUnit: DomainUnit<*, *>) {
        val schema = domainUnit.createSchema(DomainUnitSchemaHelperImpl())
        val conceptEntries = domainUnit.processDomainUnitInputData(processSession.parameterAccess, DomainUnitProcessInputDataHelperImpl(processSession, schema))

        println("Schema: $schema")
        println("InputData: $conceptEntries")

        val concepts = ConceptsValidator.validateAndResolveConcepts(conceptEntries)

        // traverse whole model and transform (adapt/calculate/transform) the missing model values
        // val conceptModelGraph = ConceptModelGraphCalculator.calculateConceptModelGraph(schema, conceptEntries)
        // println("conceptModelGraph: $conceptModelGraph")
        println("concepts: $concepts")


        val targetFilesCollector = domainUnit.processDomainUnitTargetFiles(
            processSession.parameterAccess,
            DomainUnitProcessTargetFilesDataHelperImpl(processSession, schema, concepts)
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
