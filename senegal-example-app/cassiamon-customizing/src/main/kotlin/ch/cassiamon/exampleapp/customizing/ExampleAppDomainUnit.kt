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
import ch.cassiamon.exampleapp.customizing.templates.EntitiesConcept
import ch.cassiamon.exampleapp.customizing.templates.EntityConcept
import ch.cassiamon.exampleapp.customizing.templates.db.*
import java.nio.file.Path

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

    override fun configureTemplates(registration: TemplatesRegistrationApi) {
        val outputDirectory = ExampleAppParameters.outputDirectory(parameterAccess)
        val liquibaseResourceDirectory = ExampleAppParameters.persistenceResourcePath(parameterAccess).resolve("db/changelog/")
        val persistenceSourceDirectory = ExampleAppParameters.persistencePath(parameterAccess)
        registration {

            // create description file index
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

            // create description file per entity
            newTemplate { conceptModelGraph ->
                val entities = entities(conceptModelGraph)
                val targetFilesWithModel = setOf(TargetGeneratedFileWithModel(outputDirectory.resolve("_index.description.txt"), entities))

                return@newTemplate newTemplateRenderer(targetFilesWithModel) { targetGeneratedFileWithModel: TargetGeneratedFileWithModel<EntityConcept> ->
                    return@newTemplateRenderer StringContentByteIterator(
                        EntityDescriptionTemplate.createEntitiesDescriptionTemplate(targetGeneratedFileWithModel.targetFile, targetGeneratedFileWithModel.model)
                    )
                }

            }

            // create liquibase file per DB table
            newTemplate { conceptModelGraph ->
                val targetFiles = targetFilesPerDbTable(conceptModelGraph) {
                    liquibaseResourceDirectory.resolve(it.liquibaseFileName)
                }

                return@newTemplate TemplateRenderer<DbTable>(targetFiles) { targetGeneratedFileWithModel: TargetGeneratedFileWithModel<DbTable> ->
                    return@TemplateRenderer StringContentByteIterator(LiquibaseTemplate.createLiquibaseXmlFileTemplate(targetGeneratedFileWithModel.model.first()))
                }
            }

            // create liquibase index
            newTemplate { conceptModelGraph ->
                val dbTables = entities(conceptModelGraph).map { DbTable(it) }

                val targetFiles = setOf(TargetGeneratedFileWithModel(liquibaseResourceDirectory.resolve(DbTable.liquibaseIndexFileName), dbTables))

                return@newTemplate TemplateRenderer<DbTable>(targetFiles) { targetGeneratedFileWithModel: TargetGeneratedFileWithModel<DbTable> ->
                    return@TemplateRenderer StringContentByteIterator(LiquibaseTemplate.createLiquibaseXmlIndexFileTemplate(targetGeneratedFileWithModel.model))
                }

            }

            // create jpa entity file per DB table
            newTemplate { conceptModelGraph ->
                val targetFiles = targetFilesPerDbTable(conceptModelGraph) {
                    persistenceSourceDirectory.resolve(it.jpaEntityPackage.replacePackageByDirectory()).resolve(it.jpaEntityFileName)
                }
                return@newTemplate TemplateRenderer<DbTable>(targetFiles) { targetGeneratedFileWithModel: TargetGeneratedFileWithModel<DbTable> ->
                    return@TemplateRenderer StringContentByteIterator(JpaEntityTemplate.createJpaEntityTemplate(targetGeneratedFileWithModel.model.first()))
                }
            }

            // create jpa repository file per DB table
            newTemplate { conceptModelGraph ->
                val targetFiles = targetFilesPerDbTable(conceptModelGraph) {
                    persistenceSourceDirectory.resolve(it.jpaRepositoryPackage.replacePackageByDirectory()).resolve(it.jpaRepositoryFileName)
                }
                return@newTemplate TemplateRenderer<DbTable>(targetFiles) { targetGeneratedFileWithModel: TargetGeneratedFileWithModel<DbTable> ->
                    return@TemplateRenderer StringContentByteIterator(JpaRepositoryTemplate.createJpaRepositoryTemplate(targetGeneratedFileWithModel.model.first()))
                }
            }

            // create repository file per DB table
            newTemplate { conceptModelGraph ->
                val targetFiles = targetFilesPerDbTable(conceptModelGraph) {
                    persistenceSourceDirectory.resolve(it.repositoryImplementationPackage.replacePackageByDirectory()).resolve(it.repositoryImplementationFileName)
                }
                return@newTemplate TemplateRenderer<DbTable>(targetFiles) { targetGeneratedFileWithModel: TargetGeneratedFileWithModel<DbTable> ->
                    return@TemplateRenderer StringContentByteIterator(RepositoryImplTemplate.createRepositoryImplementationTemplate(targetGeneratedFileWithModel.model.first()))
                }
            }

        }
    }

    private fun String.replacePackageByDirectory(): String {
        return this.replace(".", "/")
    }

    private fun entities(conceptModelGraph: ConceptModelGraph): List<EntityConcept> {
        return conceptModelGraph
            .conceptModelNodesByConceptName(EntitiesConceptDescription.conceptName)
            .map { EntitiesConcept(it) }
            .map { it.entities() }
            .flatten()
    }

    private fun targetFilesPerDbTable(conceptModelGraph: ConceptModelGraph, pathResolver: (dbTable: DbTable) -> Path): Set<TargetGeneratedFileWithModel<DbTable>> {
        return entities(conceptModelGraph)
            .map { DbTable(it) }
            .map { dbTable -> TargetGeneratedFileWithModel(
                pathResolver(dbTable),
                listOf(dbTable)
            ) }
            .toSet()
    }

}
