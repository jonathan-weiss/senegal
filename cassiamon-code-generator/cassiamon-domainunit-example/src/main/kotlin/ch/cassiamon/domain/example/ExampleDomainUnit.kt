package ch.cassiamon.domain.example

import ch.cassiamon.api.*
import ch.cassiamon.api.extensions.ClasspathLocation
import ch.cassiamon.api.model.ConceptIdentifier
import ch.cassiamon.api.model.ConceptModelNode
import ch.cassiamon.api.parameter.ParameterAccess
import ch.cassiamon.api.registration.*
import ch.cassiamon.api.template.helper.StringContentByteIterator
import ch.cassiamon.api.template.TargetGeneratedFileWithModel
import ch.cassiamon.api.template.TemplateRenderer
import java.nio.file.Path
import java.nio.file.Paths

class ExampleDomainUnit: DefaultDomainUnit<ExampleDomainSchema>(
    domainUnitName = DomainUnitName.of("ExampleProject"),
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
        dataCollector: InputSourceDataCollector
    ) {
        super.collectInputData(parameterAccess, extensionAccess, dataCollector)

        dataCollector
            .newConceptData(ExampleEntityConcept.conceptName, ConceptIdentifier.of("MeinTestkonzept"))
            .addFacetValue(ExampleEntityConcept.nameFacet.facetName,  "MeinTestkonzept-Name")
            .attach()

        dataCollector
            .newConceptData(ExampleEntityConcept.conceptName, ConceptIdentifier.of("MeinZweitesTestkonzept"))
            .addFacetValue(ExampleEntityConcept.nameFacet.facetName,  "MeinZweitesTestkonzept-Name")
            .attach()

    }
}
