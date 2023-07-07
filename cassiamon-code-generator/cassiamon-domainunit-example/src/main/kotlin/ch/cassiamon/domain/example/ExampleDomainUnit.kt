package ch.cassiamon.domain.example

import ch.cassiamon.api.datacollection.defaults.DefaultConceptDataCollector
import ch.cassiamon.api.ConceptIdentifier
import ch.cassiamon.api.parameter.ParameterAccess
import ch.cassiamon.api.registration.*
import ch.cassiamon.api.templating.TargetFilesCollector
import java.nio.file.Path
import java.nio.file.Paths

class ExampleDomainUnit: DefaultDomainUnit<ExampleDomainSchema>(
    schemaDefinitionClass = ExampleDomainSchema::class.java
) {

    companion object {
        val outputDirectory: Path = Paths.get("output-data")
    }
    override fun collectTargetFiles(
        parameterAccess: ParameterAccess,
        schemaInstance: ExampleDomainSchema,
        targetFilesCollector: TargetFilesCollector
    ) {
        val basePath = outputDirectory
        schemaInstance
            .getEntityConcepts()
            .forEach { entity ->
                val targetFile = basePath.resolve("${entity.entityName()}.example.txt")
                val content = ExampleTemplate.createExampleTemplate(targetFile, entity)
                targetFilesCollector.addFile(targetFile, content)
            }

        val targetIndexFile = basePath.resolve("index.example.txt")
        val content = ExampleTemplate.createExampleIndexTemplate(targetIndexFile, schemaInstance.getEntityConcepts())
        targetFilesCollector.addFile(targetIndexFile, content)

    }

    override fun collectInputData(
        parameterAccess: ParameterAccess,
        extensionAccess: InputSourceExtensionAccess,
        dataCollector: DefaultConceptDataCollector
    ) {
        super.collectInputData(parameterAccess, extensionAccess, dataCollector)

        dataCollector
            .newConceptData(ExampleEntitySchemaConstants.conceptName, ConceptIdentifier.of("MeinTestkonzept"))
            .addFacetValue(ExampleEntitySchemaConstants.exampleEntityNameFacetName,  "MeinTestkonzept-Name")
            .attach()

        dataCollector
            .newConceptData(ExampleEntitySchemaConstants.conceptName, ConceptIdentifier.of("MeinZweitesTestkonzept"))
            .addFacetValue(ExampleEntitySchemaConstants.exampleEntityNameFacetName,  "MeinZweitesTestkonzept-Name")
            .attach()

    }
}
