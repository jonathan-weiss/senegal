package ch.cassiamon.exampleapp.customizing

import ch.cassiamon.api.*
import ch.cassiamon.api.parameter.ParameterAccess
import ch.cassiamon.api.registration.*
import ch.cassiamon.api.template.helper.StringContentByteIterator
import ch.cassiamon.api.template.TargetGeneratedFileWithModel
import ch.cassiamon.api.template.TemplateRenderer
import ch.cassiamon.exampleapp.customizing.concepts.EntitiesConcept
import ch.cassiamon.exampleapp.customizing.concepts.EntityAttributeConcept
import ch.cassiamon.exampleapp.customizing.concepts.EntityConcept
import ch.cassiamon.exampleapp.customizing.templates.ExampleAppTemplate

class ExampleAppDomainUnit: DomainUnit {
    private lateinit var parameterAccess: ParameterAccess

    override val domainUnitName: DomainUnitName = DomainUnitName.of("ExampleAppProject")

    override fun provideParameters(parameterAccess: ParameterAccess) {
        this.parameterAccess = parameterAccess
    }

    override fun configureSchema(registration: SchemaRegistrationApi) {
        registration {
            newRootConcept(EntitiesConcept.conceptName) {
                addFacet(EntitiesConcept.infoDescriptionFacet)

                newChildConcept(EntityConcept.conceptName) {

                    addFacet(EntityConcept.nameFacet)
                    addFacet(EntityConcept.alternativeNameFacet)

                    newChildConcept(EntityAttributeConcept.conceptName) {
                        addFacet(EntityAttributeConcept.nameFacet)
                        addFacet(EntityAttributeConcept.typeFacet)
                    }
                }
            }

        }

    }


    override fun configureDataCollector(registration: InputSourceRegistrationApi) {
        val xmlDefinitionFile = ExampleAppParameters.xmlDefinitionFile(parameterAccess)
        registration {
            dataCollectionWithFilesInputSourceExtension(
                extensionName = ExampleAppExtensions.xmlSchemagicInputExtensionName,
                inputFiles = setOf(xmlDefinitionFile),
            )
        }

    }

    override fun configureTemplates(registration: TemplatesRegistrationApi) {
        val outputDirectory = ExampleAppParameters.outputDirectory(parameterAccess)
        registration {
            newTemplate { conceptModelGraph ->

                val targetFiles = conceptModelGraph
                    .conceptModelNodesByConceptName(EntityConcept.conceptName)
                    .map { entity -> TargetGeneratedFileWithModel(
                        outputDirectory.resolve("${entity[EntityConcept.nameFacet]}.example.txt"),
                        listOf(entity)
                    ) }
                    .toSet()


                return@newTemplate TemplateRenderer(targetFiles) { targetGeneratedFileWithModel: TargetGeneratedFileWithModel ->
                    return@TemplateRenderer StringContentByteIterator(ExampleAppTemplate.createExampleTemplate(targetGeneratedFileWithModel.targetFile, targetGeneratedFileWithModel.model.first()))
                }
            }



            newTemplate { conceptModelGraph ->

//                val targetFiles = templateNodesProvider
//                    .targetGeneratedFileForEachTemplateNode(ConceptName.of("TestConcept")) { templateNode ->
//                    Paths.get("$templateNode.conceptIdentifier.code-xyz.json")
//                }

                val targetFiles = emptySet<TargetGeneratedFileWithModel>()

                return@newTemplate TemplateRenderer(targetFiles) { targetGeneratedFileWithModel: TargetGeneratedFileWithModel ->
                    return@TemplateRenderer StringContentByteIterator(
                        "content of ${targetGeneratedFileWithModel.targetFile} is ${targetGeneratedFileWithModel.model}"
                    )
                }
            }

            // test a single node file generation
            newTemplate { conceptModelGraph ->
                val templateNodes = conceptModelGraph.conceptModelNodesByConceptName(EntityConcept.conceptName)
                val targetFilesWithModel = setOf(TargetGeneratedFileWithModel(outputDirectory.resolve("index.json"), templateNodes))

                return@newTemplate newTemplateRenderer(targetFilesWithModel) { targetGeneratedFileWithModel: TargetGeneratedFileWithModel ->
                    return@newTemplateRenderer StringContentByteIterator(
                        "content of ${targetGeneratedFileWithModel.targetFile} is ${targetGeneratedFileWithModel.model}"
                    )
                }

            }
        }
    }
}
