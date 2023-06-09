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
import ch.cassiamon.exampleapp.customizing.templates.angular.*
import ch.cassiamon.exampleapp.customizing.templates.angular.addview.AngularFrontendAddViewHtmlTemplate
import ch.cassiamon.exampleapp.customizing.templates.angular.addview.AngularFrontendAddViewScssTemplate
import ch.cassiamon.exampleapp.customizing.templates.angular.addview.AngularFrontendAddViewTypescriptTemplate
import ch.cassiamon.exampleapp.customizing.templates.angular.editview.AngularFrontendEditViewHtmlTemplate
import ch.cassiamon.exampleapp.customizing.templates.angular.editview.AngularFrontendEditViewScssTemplate
import ch.cassiamon.exampleapp.customizing.templates.angular.editview.AngularFrontendEditViewTypescriptTemplate
import ch.cassiamon.exampleapp.customizing.templates.angular.api.*
import ch.cassiamon.exampleapp.customizing.templates.angular.panelview.AngularFrontendPanelViewHtmlTemplate
import ch.cassiamon.exampleapp.customizing.templates.angular.panelview.AngularFrontendPanelViewScssTemplate
import ch.cassiamon.exampleapp.customizing.templates.angular.panelview.AngularFrontendPanelViewTypescriptTemplate
import ch.cassiamon.exampleapp.customizing.templates.angular.tableview.AngularFrontendTableViewHtmlTemplate
import ch.cassiamon.exampleapp.customizing.templates.angular.tableview.AngularFrontendTableViewScssTemplate
import ch.cassiamon.exampleapp.customizing.templates.angular.tableview.AngularFrontendTableViewTypescriptTemplate
import ch.cassiamon.exampleapp.customizing.templates.db.*
import ch.cassiamon.exampleapp.customizing.templates.kotlinmodel.*
import ch.cassiamon.exampleapp.customizing.templates.restapi.*
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
        val frontendApiSourceDirectory = ExampleAppParameters.frontendApiPath(parameterAccess)
        val frontendSourceDirectory = ExampleAppParameters.frontendPath(parameterAccess)
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


            // create REST api facade class
            newTemplate { conceptModelGraph ->
                val pathResolver = createPathResolver<RestModelClass>(frontendApiSourceDirectory,  filePrefix = "", fileSuffix = ".kt", packageNameResolver = { it.facadePackageName}, fileNameResolver = { it.facadeClassName })
                return@newTemplate useModelTemplate(conceptModelGraph,
                    createModelInstance = ::RestModelClass,
                    pathResolver = pathResolver,
                    templateFunction = RestApiFacadeTemplate::fillTemplate
                )
            }

            // create REST api transfer object
            newTemplate { conceptModelGraph ->
                val pathResolver = createPathResolver<RestModelClass>(frontendApiSourceDirectory,  filePrefix = "", fileSuffix = "TO.kt", packageNameResolver = { it.transferObjectPackageName}, fileNameResolver = { it.transferObjectBaseName })
                return@newTemplate useModelTemplate(conceptModelGraph,
                    createModelInstance = ::RestModelClass,
                    pathResolver = pathResolver,
                    templateFunction = RestApiTransferObjectTemplate::fillTemplate
                )
            }

            // create REST api transfer object create instruction
            newTemplate { conceptModelGraph ->
                val pathResolver = createPathResolver<RestModelClass>(frontendApiSourceDirectory,  filePrefix = "Create", fileSuffix = "InstructionTO.kt", packageNameResolver = { it.transferObjectPackageName}, fileNameResolver = { it.transferObjectBaseName })
                return@newTemplate useModelTemplate(conceptModelGraph,
                    createModelInstance = ::RestModelClass,
                    pathResolver = pathResolver,
                    templateFunction = RestApiTransferObjectCreateInstructionTemplate::fillTemplate
                )
            }

            // create REST api transfer object update instruction
            newTemplate { conceptModelGraph ->
                val pathResolver = createPathResolver<RestModelClass>(frontendApiSourceDirectory,  filePrefix = "Update", fileSuffix = "InstructionTO.kt", packageNameResolver = { it.transferObjectPackageName}, fileNameResolver = { it.transferObjectBaseName })
                return@newTemplate useModelTemplate(conceptModelGraph,
                    createModelInstance = ::RestModelClass,
                    pathResolver = pathResolver,
                    templateFunction = RestApiTransferObjectUpdateInstructionTemplate::fillTemplate
                )
            }

            // create REST api transfer object delete instruction
            newTemplate { conceptModelGraph ->
                val pathResolver = createPathResolver<RestModelClass>(frontendApiSourceDirectory,  filePrefix = "Delete", fileSuffix = "InstructionTO.kt", packageNameResolver = { it.transferObjectPackageName}, fileNameResolver = { it.transferObjectBaseName })
                return@newTemplate useModelTemplate(conceptModelGraph,
                    createModelInstance = ::RestModelClass,
                    pathResolver = pathResolver,
                    templateFunction = RestApiTransferObjectDeleteInstructionTemplate::fillTemplate
                )
            }


            // create REST api controller
            newTemplate { conceptModelGraph ->
                val pathResolver = createPathResolver<RestModelClass>(frontendApiSourceDirectory,  filePrefix = "", fileSuffix = ".kt", packageNameResolver = { it.controllerPackageName}, fileNameResolver = { it.controllerClassName })
                return@newTemplate useModelTemplate(conceptModelGraph,
                    createModelInstance = ::RestModelClass,
                    pathResolver = pathResolver,
                    templateFunction = RestApiControllerTemplate::fillTemplate
                )
            }

            // create angular modules file
            newTemplate { conceptModelGraph ->
                val path = frontendSourceDirectory.resolve("generated-entities.module.ts")
                val targetFile = targetFileWithAllEntities(conceptModelGraph, ::AngularModelClass, path)

                return@newTemplate TemplateRenderer(setOf(targetFile)) { targetGeneratedFileWithModel: TargetGeneratedFileWithModel<AngularModelClass> ->
                    return@TemplateRenderer StringContentByteIterator(AngularFrontendModulesTemplate.fillTemplate(targetGeneratedFileWithModel.model))
                }
            }

            // create angular routing file
            newTemplate { conceptModelGraph ->
                val path = frontendSourceDirectory.resolve("generated-entities-routing.module.ts")
                val targetFile = targetFileWithAllEntities(conceptModelGraph, ::AngularModelClass, path)

                return@newTemplate TemplateRenderer(setOf(targetFile)) { targetGeneratedFileWithModel: TargetGeneratedFileWithModel<AngularModelClass> ->
                    return@TemplateRenderer StringContentByteIterator(AngularFrontendRoutingTemplate.fillTemplate(targetGeneratedFileWithModel.model))
                }
            }

            val angularToModelPathResolver = createAngularPathResolver<AngularModelClass>(frontendSourceDirectory,  pathResolver = { "${it.entityFileName}/api/${it.entityFileName}-to.model" }, filenameSuffix = ".ts")
            val angularServicePathResolver = createAngularPathResolver<AngularModelClass>(frontendSourceDirectory,  pathResolver = { "${it.entityFileName}/api/${it.entityFileName}-api.service" }, filenameSuffix = ".ts")

            val angularCreateInstructionPathResolver = createAngularPathResolver<AngularModelClass>(frontendSourceDirectory,  pathResolver = { "${it.entityFileName}/api/create-${it.entityFileName}-instruction-to.model" }, filenameSuffix = ".ts")
            val angularUpdateInstructionPathResolver = createAngularPathResolver<AngularModelClass>(frontendSourceDirectory,  pathResolver = { "${it.entityFileName}/api/update-${it.entityFileName}-instruction-to.model" }, filenameSuffix = ".ts")
            val angularDeleteInstructionPathResolver = createAngularPathResolver<AngularModelClass>(frontendSourceDirectory,  pathResolver = { "${it.entityFileName}/api/delete-${it.entityFileName}-instruction-to.model" }, filenameSuffix = ".ts")

            val angularPanelViewComponentPathResolver = { it:AngularModelClass -> "${it.entityFileName}/component/${it.entityFileName}-panel-view/${it.entityFileName}-panel-view.component" }
            val angularPanelViewComponentTsPathResolver = createAngularPathResolver(frontendSourceDirectory,  pathResolver = angularPanelViewComponentPathResolver, filenameSuffix = ".ts")
            val angularPanelViewComponentScssPathResolver = createAngularPathResolver(frontendSourceDirectory,  pathResolver = angularPanelViewComponentPathResolver, filenameSuffix = ".scss")
            val angularPanelViewComponentHtmlPathResolver = createAngularPathResolver(frontendSourceDirectory,  pathResolver = angularPanelViewComponentPathResolver, filenameSuffix = ".html")

            val angularTableViewComponentPathResolver = { it:AngularModelClass -> "${it.entityFileName}/component/${it.entityFileName}-table-view/${it.entityFileName}-table-view.component" }
            val angularTableViewComponentTsPathResolver = createAngularPathResolver(frontendSourceDirectory,  pathResolver = angularTableViewComponentPathResolver, filenameSuffix = ".ts")
            val angularTableViewComponentScssPathResolver = createAngularPathResolver(frontendSourceDirectory,  pathResolver = angularTableViewComponentPathResolver, filenameSuffix = ".scss")
            val angularTableViewComponentHtmlPathResolver = createAngularPathResolver(frontendSourceDirectory,  pathResolver = angularTableViewComponentPathResolver, filenameSuffix = ".html")

            val angularEditViewComponentPathResolver = { it:AngularModelClass -> "${it.entityFileName}/component/${it.entityFileName}-edit-view/${it.entityFileName}-edit-view.component" }
            val angularEditViewComponentTsPathResolver = createAngularPathResolver(frontendSourceDirectory,  pathResolver = angularEditViewComponentPathResolver, filenameSuffix = ".ts")
            val angularEditViewComponentScssPathResolver = createAngularPathResolver(frontendSourceDirectory,  pathResolver = angularEditViewComponentPathResolver, filenameSuffix = ".scss")
            val angularEditViewComponentHtmlPathResolver = createAngularPathResolver(frontendSourceDirectory,  pathResolver = angularEditViewComponentPathResolver, filenameSuffix = ".html")

            val angularAddViewComponentPathResolver = { it:AngularModelClass -> "${it.entityFileName}/component/${it.entityFileName}-add-view/${it.entityFileName}-add-view.component" }
            val angularAddViewComponentTsPathResolver = createAngularPathResolver(frontendSourceDirectory,  pathResolver = angularAddViewComponentPathResolver, filenameSuffix = ".ts")
            val angularAddViewComponentScssPathResolver = createAngularPathResolver(frontendSourceDirectory,  pathResolver = angularAddViewComponentPathResolver, filenameSuffix = ".scss")
            val angularAddViewComponentHtmlPathResolver = createAngularPathResolver(frontendSourceDirectory,  pathResolver = angularAddViewComponentPathResolver, filenameSuffix = ".html")

            // create angular model
            newTemplate { conceptModelGraph ->
                return@newTemplate useModelTemplate(conceptModelGraph,
                    createModelInstance = ::AngularModelClass,
                    pathResolver = angularToModelPathResolver,
                    templateFunction = AngularFrontendModelToTemplate::fillTemplate
                )
            }

            // create angular service
            newTemplate { conceptModelGraph ->
                return@newTemplate useModelTemplate(conceptModelGraph,
                    createModelInstance = ::AngularModelClass,
                    pathResolver = angularServicePathResolver,
                    templateFunction = AngularFrontendServiceToTemplate::fillTemplate
                )
            }

            // create angular create instruction
            newTemplate { conceptModelGraph ->
                return@newTemplate useModelTemplate(conceptModelGraph,
                    createModelInstance = ::AngularModelClass,
                    pathResolver = angularCreateInstructionPathResolver,
                    templateFunction = AngularFrontendCreateInstructionToTemplate::fillTemplate
                )
            }

            // create angular update instruction
            newTemplate { conceptModelGraph ->
                return@newTemplate useModelTemplate(conceptModelGraph,
                    createModelInstance = ::AngularModelClass,
                    pathResolver = angularUpdateInstructionPathResolver,
                    templateFunction = AngularFrontendUpdateInstructionToTemplate::fillTemplate
                )
            }

            // create angular delete instruction
            newTemplate { conceptModelGraph ->
                return@newTemplate useModelTemplate(conceptModelGraph,
                    createModelInstance = ::AngularModelClass,
                    pathResolver = angularDeleteInstructionPathResolver,
                    templateFunction = AngularFrontendDeleteInstructionToTemplate::fillTemplate
                )
            }

            // create angular panel view
            newTemplate { conceptModelGraph -> useModelTemplate(conceptModelGraph,
                createModelInstance = ::AngularModelClass,
                pathResolver = angularPanelViewComponentHtmlPathResolver,
                templateFunction = AngularFrontendPanelViewHtmlTemplate::fillTemplate
            )
            }
            newTemplate { conceptModelGraph -> useModelTemplate(conceptModelGraph,
                    createModelInstance = ::AngularModelClass,
                    pathResolver = angularPanelViewComponentScssPathResolver,
                    templateFunction = AngularFrontendPanelViewScssTemplate::fillTemplate
                )
            }
            newTemplate { conceptModelGraph -> useModelTemplate(conceptModelGraph,
                    createModelInstance = ::AngularModelClass,
                    pathResolver = angularPanelViewComponentTsPathResolver,
                    templateFunction = AngularFrontendPanelViewTypescriptTemplate::fillTemplate
                )
            }

            // create angular table view
            newTemplate { conceptModelGraph -> useModelTemplate(conceptModelGraph,
                createModelInstance = ::AngularModelClass,
                pathResolver = angularTableViewComponentHtmlPathResolver,
                templateFunction = AngularFrontendTableViewHtmlTemplate::fillTemplate
            )
            }
            newTemplate { conceptModelGraph -> useModelTemplate(conceptModelGraph,
                createModelInstance = ::AngularModelClass,
                pathResolver = angularTableViewComponentScssPathResolver,
                templateFunction = AngularFrontendTableViewScssTemplate::fillTemplate
            )
            }
            newTemplate { conceptModelGraph -> useModelTemplate(conceptModelGraph,
                createModelInstance = ::AngularModelClass,
                pathResolver = angularTableViewComponentTsPathResolver,
                templateFunction = AngularFrontendTableViewTypescriptTemplate::fillTemplate
            )
            }
            
            // create angular edit view
            newTemplate { conceptModelGraph -> useModelTemplate(conceptModelGraph,
                createModelInstance = ::AngularModelClass,
                pathResolver = angularEditViewComponentHtmlPathResolver,
                templateFunction = AngularFrontendEditViewHtmlTemplate::fillTemplate
            )
            }
            newTemplate { conceptModelGraph -> useModelTemplate(conceptModelGraph,
                createModelInstance = ::AngularModelClass,
                pathResolver = angularEditViewComponentScssPathResolver,
                templateFunction = AngularFrontendEditViewScssTemplate::fillTemplate
            )
            }
            newTemplate { conceptModelGraph -> useModelTemplate(conceptModelGraph,
                createModelInstance = ::AngularModelClass,
                pathResolver = angularEditViewComponentTsPathResolver,
                templateFunction = AngularFrontendEditViewTypescriptTemplate::fillTemplate
            )
            }
            
            // create angular add view
            newTemplate { conceptModelGraph -> useModelTemplate(conceptModelGraph,
                createModelInstance = ::AngularModelClass,
                pathResolver = angularAddViewComponentHtmlPathResolver,
                templateFunction = AngularFrontendAddViewHtmlTemplate::fillTemplate
            )
            }
            newTemplate { conceptModelGraph -> useModelTemplate(conceptModelGraph,
                createModelInstance = ::AngularModelClass,
                pathResolver = angularAddViewComponentScssPathResolver,
                templateFunction = AngularFrontendAddViewScssTemplate::fillTemplate
            )
            }
            newTemplate { conceptModelGraph -> useModelTemplate(conceptModelGraph,
                createModelInstance = ::AngularModelClass,
                pathResolver = angularAddViewComponentTsPathResolver,
                templateFunction = AngularFrontendAddViewTypescriptTemplate::fillTemplate
            )
            }
            
        }
    }

    private fun <T> targetFileWithAllEntities(conceptModelGraph: ConceptModelGraph, createModelInstance: (EntityConcept) -> T, targetFile: Path): TargetGeneratedFileWithModel<T> {
        val models = entities(conceptModelGraph)
            .map { createModelInstance(it) }
        return TargetGeneratedFileWithModel(targetFile, models)
    }


    private fun <T> createAngularPathResolver(sourceDirectory: Path, pathResolver: (T) -> String, filenameSuffix: String): (T)-> Path {
        return { model: T ->
            sourceDirectory.resolve(pathResolver(model) + filenameSuffix)
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
