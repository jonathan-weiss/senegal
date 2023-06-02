package ch.cassiamon.exampleapp.customizing

import ch.cassiamon.api.*
import ch.cassiamon.api.model.ConceptModelGraph
import ch.cassiamon.api.parameter.ParameterAccess
import ch.cassiamon.api.registration.*
import ch.cassiamon.api.template.helper.StringContentByteIterator
import ch.cassiamon.api.template.TargetGeneratedFileWithModel
import ch.cassiamon.api.template.TemplateRenderer
import ch.cassiamon.exampleapp.customizing.concepts.EntitiesConceptDescription
import ch.cassiamon.exampleapp.customizing.concepts.EntityAttributeConceptDescription
import ch.cassiamon.exampleapp.customizing.concepts.EntityConceptDescription
import ch.cassiamon.exampleapp.customizing.templates.EntityDescriptionTemplate
import ch.cassiamon.exampleapp.customizing.wrapper.EntitiesConcept
import ch.cassiamon.exampleapp.customizing.wrapper.EntityConcept

class ExampleAppDomainUnit: DomainUnit {
    private lateinit var parameterAccess: ParameterAccess

    override val domainUnitName: DomainUnitName = DomainUnitName.of("ExampleAppProject")

    override fun provideParameters(parameterAccess: ParameterAccess) {
        this.parameterAccess = parameterAccess
    }

    override fun configureSchema(registration: SchemaRegistrationApi) {
        registration {
            newRootConcept(EntitiesConceptDescription.conceptName) {
                addFacet(EntitiesConceptDescription.infoDescriptionFacet)

                newChildConcept(EntityConceptDescription.conceptName) {

                    addFacet(EntityConceptDescription.nameFacet)
                    addFacet(EntityConceptDescription.alternativeNameFacet)

                    newChildConcept(EntityAttributeConceptDescription.conceptName) {
                        addFacet(EntityAttributeConceptDescription.nameFacet)
                        addFacet(EntityAttributeConceptDescription.typeFacet)
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

    private fun entities(conceptModelGraph: ConceptModelGraph): List<EntityConcept> {
        return conceptModelGraph
            .conceptModelNodesByConceptName(EntitiesConceptDescription.conceptName)
            .map { EntitiesConcept(it) }
            .map { it.entities() }
            .flatten()
    }

    override fun configureTemplates(registration: TemplatesRegistrationApi) {
        val outputDirectory = ExampleAppParameters.outputDirectory(parameterAccess)
        registration {
            newTemplate { conceptModelGraph ->
                val entities = entities(conceptModelGraph)

                val targetFiles = entities
                    .map { entity -> TargetGeneratedFileWithModel(
                        outputDirectory.resolve("${entity.name}.description.txt"),
                        listOf(entity)
                    ) }
                    .toSet()


                return@newTemplate TemplateRenderer<EntityConcept>(targetFiles) { targetGeneratedFileWithModel: TargetGeneratedFileWithModel<EntityConcept> ->
                    return@TemplateRenderer StringContentByteIterator(EntityDescriptionTemplate.createEntityDescriptionTemplate(targetGeneratedFileWithModel.targetFile, targetGeneratedFileWithModel.model.first()))
                }
            }

            // test a single node file generation
            newTemplate { conceptModelGraph ->
                val entities = entities(conceptModelGraph)
                val targetFilesWithModel = setOf(TargetGeneratedFileWithModel(outputDirectory.resolve("_index.description.txt"), entities))

                return@newTemplate newTemplateRenderer(targetFilesWithModel) { targetGeneratedFileWithModel: TargetGeneratedFileWithModel<EntityConcept> ->
                    return@newTemplateRenderer StringContentByteIterator(
                        EntityDescriptionTemplate.createEntitiesDescriptionTemplate(targetGeneratedFileWithModel.targetFile, targetGeneratedFileWithModel.model)
                    )
                }

            }
        }
    }
}
