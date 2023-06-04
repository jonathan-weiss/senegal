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
import ch.cassiamon.exampleapp.customizing.templates.kotlinmodel.*
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
        val domainSourceDirectory = ExampleAppParameters.domainPath(parameterAccess)
        val sharedDomainSourceDirectory = ExampleAppParameters.sharedDomainPath(parameterAccess)
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
                val pathResolver = createPathResolver<DbTable>(liquibaseResourceDirectory,  filePrefix = "", fileSuffix = "", packageNameResolver = { "" }, fileNameResolver = { it.liquibaseFileName })
                return@newTemplate useModelTemplate(conceptModelGraph,
                    createModelInstance = ::DbTable,
                    pathResolver = pathResolver,
                    templateFunction = LiquibaseTemplate::createLiquibaseXmlFileTemplate
                )
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
                val pathResolver = createPathResolver<DbTable>(persistenceSourceDirectory,  filePrefix = "", fileSuffix = ".kt", packageNameResolver = { it.jpaEntityPackage}, fileNameResolver = { it.jpaEntityName })
                return@newTemplate useModelTemplate(conceptModelGraph,
                    createModelInstance = ::DbTable,
                    pathResolver = pathResolver,
                    templateFunction = JpaEntityTemplate::fillTemplate
                )
            }

            // create jpa repository file per DB table
            newTemplate { conceptModelGraph ->
                val pathResolver = createPathResolver<DbTable>(persistenceSourceDirectory,  filePrefix = "", fileSuffix = ".kt", packageNameResolver = { it.jpaRepositoryPackage}, fileNameResolver = { it.jpaRepositoryName })
                return@newTemplate useModelTemplate(conceptModelGraph,
                    createModelInstance = ::DbTable,
                    pathResolver = pathResolver,
                    templateFunction = JpaRepositoryTemplate::fillTemplate
                )
            }

            // create repository file per DB table
            newTemplate { conceptModelGraph ->
                val pathResolver = createPathResolver<DbTable>(persistenceSourceDirectory,  filePrefix = "", fileSuffix = ".kt", packageNameResolver = { it.repositoryImplementationPackage}, fileNameResolver = { it.repositoryImplementationName })
                return@newTemplate useModelTemplate(conceptModelGraph,
                    createModelInstance = ::DbTable,
                    pathResolver = pathResolver,
                    templateFunction = RepositoryImplTemplate::fillTemplate
                )
            }


            // create kotlin model class
            newTemplate { conceptModelGraph ->
                val pathResolver = createPathResolver<KotlinModelClass>(domainSourceDirectory,  filePrefix = "", fileSuffix = ".kt", packageNameResolver = { it.kotlinPackage}, fileNameResolver = { it.kotlinClassName })
                return@newTemplate useModelTemplate(conceptModelGraph,
                    createModelInstance = ::KotlinModelClass,
                    pathResolver = pathResolver,
                    templateFunction = KotlinModelClassTemplate::fillTemplate
                )
            }

            // create kotlin model id class
            newTemplate { conceptModelGraph ->
                val pathResolver = createPathResolver<KotlinModelClass>(domainSourceDirectory,  filePrefix = "", fileSuffix = "Id.kt", packageNameResolver = { it.kotlinPackage}, fileNameResolver = { it.kotlinClassName })
                return@newTemplate useModelTemplate(conceptModelGraph,
                    createModelInstance = ::KotlinModelClass,
                    pathResolver = pathResolver,
                    templateFunction = KotlinModelIdClassTemplate::fillTemplate
                )
            }

            // create kotlin model repository class
            newTemplate { conceptModelGraph ->
                val pathResolver = createPathResolver<KotlinModelClass>(domainSourceDirectory,  filePrefix = "", fileSuffix = "Repository.kt", packageNameResolver = { it.kotlinPackage}, fileNameResolver = { it.kotlinClassName })
                return@newTemplate useModelTemplate(conceptModelGraph,
                    createModelInstance = ::KotlinModelClass,
                    pathResolver = pathResolver,
                    templateFunction = KotlinModelRepositoryTemplate::fillTemplate
                )
            }

            // create kotlin model create instruction class
            newTemplate { conceptModelGraph ->
                val pathResolver = createPathResolver<KotlinModelClass>(domainSourceDirectory,  filePrefix = "Create", fileSuffix = "Instruction.kt", packageNameResolver = { it.kotlinPackage}, fileNameResolver = { it.kotlinClassName })
                return@newTemplate useModelTemplate(conceptModelGraph,
                    createModelInstance = ::KotlinModelClass,
                    pathResolver = pathResolver,
                    templateFunction = KotlinModelCreateInstructionTemplate::fillTemplate
                )
            }

            // create kotlin model update instruction class
            newTemplate { conceptModelGraph ->
                val pathResolver = createPathResolver<KotlinModelClass>(domainSourceDirectory,  filePrefix = "Update", fileSuffix = "Instruction.kt", packageNameResolver = { it.kotlinPackage}, fileNameResolver = { it.kotlinClassName })
                return@newTemplate useModelTemplate(conceptModelGraph,
                    createModelInstance = ::KotlinModelClass,
                    pathResolver = pathResolver,
                    templateFunction = KotlinModelUpdateInstructionTemplate::fillTemplate
                )
            }

            // create kotlin model create delete instruction class
            newTemplate { conceptModelGraph ->
                val pathResolver = createPathResolver<KotlinModelClass>(domainSourceDirectory,  filePrefix = "Delete", fileSuffix = "Instruction.kt", packageNameResolver = { it.kotlinPackage}, fileNameResolver = { it.kotlinClassName })
                return@newTemplate useModelTemplate(conceptModelGraph,
                    createModelInstance = ::KotlinModelClass,
                    pathResolver = pathResolver,
                    templateFunction = KotlinModelDeleteInstructionTemplate::fillTemplate
                )
            }


            // create kotlin model service class
            newTemplate { conceptModelGraph ->
                val pathResolver = createPathResolver<KotlinModelClass>(domainSourceDirectory,  filePrefix = "", fileSuffix = "Service.kt", packageNameResolver = { it.kotlinPackage}, fileNameResolver = { it.kotlinClassName })
                return@newTemplate useModelTemplate(conceptModelGraph,
                    createModelInstance = ::KotlinModelClass,
                    pathResolver = pathResolver,
                    templateFunction = KotlinModelServiceTemplate::fillTemplate
                )
            }

        }
    }

    private fun <T> createPathResolver(sourceDirectory: Path, packageNameResolver: (T) -> String, filePrefix: String, fileSuffix: String, fileNameResolver: (T) -> String): (T)-> Path {
        return { model: T ->
            sourceDirectory.resolve(packageNameResolver(model).replacePackageByDirectory()).resolve("${filePrefix}${fileNameResolver(model)}${fileSuffix}")
        }
    }

    private fun <T> useModelTemplate(conceptModelGraph: ConceptModelGraph, createModelInstance: (EntityConcept) -> T, pathResolver: (T)-> Path, templateFunction: (model: T) -> String): TemplateRenderer<T> {
        val targetFiles = targetFilesPerEntity(conceptModelGraph, createModelInstance, pathResolver)
        return TemplateRenderer(targetFiles) { targetGeneratedFileWithModel: TargetGeneratedFileWithModel<T> ->
            return@TemplateRenderer StringContentByteIterator(templateFunction(targetGeneratedFileWithModel.model.first()))
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

    private fun <T> targetFilesPerEntity(conceptModelGraph: ConceptModelGraph, createModelInstance: (EntityConcept) -> T, pathResolver: (T) -> Path): Set<TargetGeneratedFileWithModel<T>> {
        return entities(conceptModelGraph)
            .map { createModelInstance(it) }
            .map { model -> TargetGeneratedFileWithModel(
                pathResolver(model),
                listOf(model)
            ) }
            .toSet()
    }


}
