package ch.cassiamon.engine.process

import ch.cassiamon.api.process.DomainUnit
import ch.cassiamon.engine.process.datacollection.DomainUnitDataCollectionHelperImpl
import ch.cassiamon.engine.process.conceptgraph.ConceptResolver
import ch.cassiamon.engine.process.schema.DomainUnitSchemaHelperImpl
import ch.cassiamon.engine.process.templating.DomainUnitProcessTargetFilesDataHelperImpl
import ch.cassiamon.engine.process.templating.TargetFileCollectionProvider
import kotlin.io.path.absolutePathString

class EngineProcess(private val processSession: ProcessSession) {



    fun runProcess() {
        processSession.domainUnits.forEach { domainUnit -> processDomainUnit(domainUnit) }
    }

    private fun processDomainUnit(domainUnit: DomainUnit<*, *>) {
        val schema = domainUnit.createSchema(DomainUnitSchemaHelperImpl())
        println("Schema: $schema")
        val conceptData = domainUnit.processDomainUnitInputData(processSession.parameterAccess, DomainUnitDataCollectionHelperImpl(processSession, schema))

        println("InputData: $conceptData")

        val conceptGraph = ConceptResolver.validateAndResolveConcepts(schema, conceptData)

        println("Concepts: $conceptGraph")


        val targetFilesCollector = domainUnit.processDomainUnitTargetFiles(
            processSession.parameterAccess,
            DomainUnitProcessTargetFilesDataHelperImpl(conceptGraph)
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
