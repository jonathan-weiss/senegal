package ch.cassiamon.exampleapp.customizing

import ch.cassiamon.api.extensions.ExtensionName
import ch.cassiamon.api.parameter.ParameterAccess
import ch.cassiamon.api.process.DomainUnit
import ch.cassiamon.api.process.datacollection.defaults.DefaultConceptDataCollector
import ch.cassiamon.api.process.datacollection.extensions.DataCollectionExtensionAccess
import ch.cassiamon.api.process.templating.TargetFilesCollector
import ch.cassiamon.exampleapp.customizing.templates.EntitiesSchema
import ch.cassiamon.exampleapp.customizing.templates.EntityDescriptionTemplate
import ch.cassiamon.exampleapp.customizing.templates.angular.AngularFrontendModulesTemplate
import ch.cassiamon.exampleapp.customizing.templates.angular.AngularFrontendRoutingTemplate
import ch.cassiamon.exampleapp.customizing.templates.angular.AngularModelClass
import ch.cassiamon.exampleapp.customizing.templates.angular.addview.AngularFrontendAddViewHtmlTemplate
import ch.cassiamon.exampleapp.customizing.templates.angular.addview.AngularFrontendAddViewScssTemplate
import ch.cassiamon.exampleapp.customizing.templates.angular.addview.AngularFrontendAddViewTypescriptTemplate
import ch.cassiamon.exampleapp.customizing.templates.angular.api.*
import ch.cassiamon.exampleapp.customizing.templates.angular.editview.AngularFrontendEditViewHtmlTemplate
import ch.cassiamon.exampleapp.customizing.templates.angular.editview.AngularFrontendEditViewScssTemplate
import ch.cassiamon.exampleapp.customizing.templates.angular.editview.AngularFrontendEditViewTypescriptTemplate
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

class ExampleAppDomainUnit: DomainUnit<EntitiesSchema, DefaultConceptDataCollector>(
    schemaDefinitionClass = EntitiesSchema::class.java,
    inputDefinitionClass = DefaultConceptDataCollector::class.java,
) {

    override fun collectInputData(
        parameterAccess: ParameterAccess,
        extensionAccess: DataCollectionExtensionAccess,
        dataCollector: DefaultConceptDataCollector
    ) {
        val xmlDefinitionFile = ExampleAppParameters.xmlDefinitionFile(parameterAccess)
        extensionAccess.collectWithDataCollectionFromFilesExtension(
            extensionName = ExtensionName.of("XmlSchemagicInputExtension"),
            inputFiles = setOf(xmlDefinitionFile),
        )
    }

    private fun toTargetFilePath(basePath: Path, packageName: String, fileName: String): Path {
        return basePath.resolve(packageName.replace(".", "/")).resolve(fileName)
    }

    override fun collectTargetFiles(
        parameterAccess: ParameterAccess,
        schemaInstance: EntitiesSchema,
        targetFilesCollector: TargetFilesCollector
    ) {
        val outputDirectory = ExampleAppParameters.outputDirectory(parameterAccess)
        val liquibaseResourceDirectory = ExampleAppParameters.persistenceResourcePath(parameterAccess).resolve("db/changelog/")
        val persistenceSourceDirectory = ExampleAppParameters.persistencePath(parameterAccess)
        val domainSourceDirectory = ExampleAppParameters.domainPath(parameterAccess)
        val sharedDomainSourceDirectory = ExampleAppParameters.sharedDomainPath(parameterAccess)
        val frontendApiSourceDirectory = ExampleAppParameters.frontendApiPath(parameterAccess)
        val frontendSourceDirectory = ExampleAppParameters.frontendPath(parameterAccess)

        // create description file per entity
        schemaInstance.entities().forEach {
            val descriptionFilePath = outputDirectory.resolve("${it.getName()}.description.txt")
            targetFilesCollector.addFile(
                targetFile = descriptionFilePath,
                fileContent = EntityDescriptionTemplate.createEntityDescriptionTemplate(descriptionFilePath, it)
            )
        }

        // create description file index
        val descriptionFilePath = outputDirectory.resolve("_index.description.txt")
        targetFilesCollector.addFile(
            targetFile = descriptionFilePath,
            fileContent = EntityDescriptionTemplate.createEntitiesDescriptionTemplate(descriptionFilePath, schemaInstance.entities())
        )

        // create liquibase file per DB table
        schemaInstance.entities().map { DbTable(it) }.forEach { dbTable ->
            targetFilesCollector.addFile(
                targetFile = liquibaseResourceDirectory.resolve(dbTable.liquibaseFileName),
                fileContent = LiquibaseTemplate.createLiquibaseXmlFileTemplate(dbTable),
            )
        }

        // create liquibase index
        targetFilesCollector.addFile(
            targetFile = liquibaseResourceDirectory.resolve(DbTable.liquibaseIndexFileName),
            fileContent = LiquibaseTemplate.createLiquibaseXmlIndexFileTemplate(schemaInstance.entities().map { DbTable(it) }),
        )

        schemaInstance.entities().map { DbTable(it) }.forEach { dbTable ->
            // create jpa entity file per DB table
            targetFilesCollector.addFile(
                targetFile = toTargetFilePath(persistenceSourceDirectory, dbTable.jpaEntityPackage, "${dbTable.jpaEntityName}.kt"),
                fileContent = JpaEntityTemplate.fillTemplate(dbTable),
            )


         // create jpa repository file per DB table
            targetFilesCollector.addFile(
                targetFile = toTargetFilePath(persistenceSourceDirectory, dbTable.jpaRepositoryPackage, "${dbTable.jpaRepositoryName}.kt"),
                fileContent = JpaRepositoryTemplate.fillTemplate(dbTable),
            )

            // create repository file per DB table
            targetFilesCollector.addFile(
                targetFile = toTargetFilePath(persistenceSourceDirectory, dbTable.repositoryImplementationPackage, "${dbTable.repositoryImplementationName}.kt"),
                fileContent = RepositoryImplTemplate.fillTemplate(dbTable),
            )
        }

        schemaInstance.entities().map { KotlinModelClass(it) }.forEach { kotlinModelClass ->
            // create kotlin model class
            targetFilesCollector.addFile(
                targetFile = toTargetFilePath(domainSourceDirectory, kotlinModelClass.kotlinPackage, "${kotlinModelClass.kotlinClassName}.kt"),
                fileContent = KotlinModelClassTemplate.fillTemplate(kotlinModelClass),
            )

            // create kotlin model id class
            targetFilesCollector.addFile(
                targetFile = toTargetFilePath(domainSourceDirectory, kotlinModelClass.kotlinPackage, "${kotlinModelClass.kotlinClassName}Id.kt"),
                fileContent = KotlinModelIdClassTemplate.fillTemplate(kotlinModelClass),
            )

            // create kotlin model repository class
            targetFilesCollector.addFile(
                targetFile = toTargetFilePath(domainSourceDirectory, kotlinModelClass.kotlinPackage, "${kotlinModelClass.kotlinClassName}Repository.kt"),
                fileContent = KotlinModelRepositoryTemplate.fillTemplate(kotlinModelClass),
            )

            // create kotlin model create instruction class
            targetFilesCollector.addFile(
                targetFile = toTargetFilePath(domainSourceDirectory, kotlinModelClass.kotlinPackage, "Create${kotlinModelClass.kotlinClassName}Instruction.kt"),
                fileContent = KotlinModelCreateInstructionTemplate.fillTemplate(kotlinModelClass),
            )

            // create kotlin model update instruction class
            targetFilesCollector.addFile(
                targetFile = toTargetFilePath(domainSourceDirectory, kotlinModelClass.kotlinPackage, "Update${kotlinModelClass.kotlinClassName}Instruction.kt"),
                fileContent = KotlinModelUpdateInstructionTemplate.fillTemplate(kotlinModelClass),
            )

            // create kotlin model create delete instruction class
            targetFilesCollector.addFile(
                targetFile = toTargetFilePath(domainSourceDirectory, kotlinModelClass.kotlinPackage, "Delete${kotlinModelClass.kotlinClassName}Instruction.kt"),
                fileContent = KotlinModelDeleteInstructionTemplate.fillTemplate(kotlinModelClass),
            )


            // create kotlin model service class
            targetFilesCollector.addFile(
                targetFile = toTargetFilePath(domainSourceDirectory, kotlinModelClass.kotlinPackage, "${kotlinModelClass.kotlinClassName}Service.kt"),
                fileContent = KotlinModelServiceTemplate.fillTemplate(kotlinModelClass),
            )
        }

        schemaInstance.entities().map { RestModelClass(it) }.forEach { restModelClass ->
            // create REST api facade class
            targetFilesCollector.addFile(
                targetFile = toTargetFilePath(frontendApiSourceDirectory, restModelClass.facadePackageName, "${restModelClass.facadeClassName}.kt"),
                fileContent = RestApiFacadeTemplate.fillTemplate(restModelClass),
            )

            // create REST api transfer object
            targetFilesCollector.addFile(
                targetFile = toTargetFilePath(frontendApiSourceDirectory, restModelClass.transferObjectPackageName, "${restModelClass.transferObjectBaseName}TO.kt"),
                fileContent = RestApiTransferObjectTemplate.fillTemplate(restModelClass),
            )

            // create REST api transfer object create instruction
            targetFilesCollector.addFile(
                targetFile = toTargetFilePath(frontendApiSourceDirectory, restModelClass.transferObjectPackageName, "Create${restModelClass.transferObjectBaseName}InstructionTO.kt"),
                fileContent = RestApiTransferObjectCreateInstructionTemplate.fillTemplate(restModelClass),
            )

            // create REST api transfer object update instruction
            targetFilesCollector.addFile(
                targetFile = toTargetFilePath(frontendApiSourceDirectory, restModelClass.transferObjectPackageName, "Update${restModelClass.transferObjectBaseName}InstructionTO.kt"),
                fileContent = RestApiTransferObjectUpdateInstructionTemplate.fillTemplate(restModelClass),
            )

            // create REST api transfer object delete instruction
            targetFilesCollector.addFile(
                targetFile = toTargetFilePath(frontendApiSourceDirectory, restModelClass.transferObjectPackageName, "Delete${restModelClass.facadeClassName}InstructionTO.kt"),
                fileContent = RestApiTransferObjectDeleteInstructionTemplate.fillTemplate(restModelClass),
            )

            // create REST api controller
            targetFilesCollector.addFile(
                targetFile = toTargetFilePath(frontendApiSourceDirectory, restModelClass.controllerPackageName, "${restModelClass.controllerClassName}.kt"),
                fileContent = RestApiControllerTemplate.fillTemplate(restModelClass),
            )
        }

        // create angular modules file
        targetFilesCollector.addFile(
            targetFile = frontendSourceDirectory.resolve("generated-entities.module.ts"),
            fileContent = AngularFrontendModulesTemplate.fillTemplate(schemaInstance.entities().map { AngularModelClass(it) }),
        )

        // create angular routing file
        targetFilesCollector.addFile(
            targetFile = frontendSourceDirectory.resolve("generated-entities-routing.module.ts"),
            fileContent = AngularFrontendRoutingTemplate.fillTemplate(schemaInstance.entities().map { AngularModelClass(it) }),
        )

        schemaInstance.entities().map { AngularModelClass(it) }.forEach { angularModelClass ->
            // create angular model
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("${angularModelClass.entityFileName}/api/${angularModelClass.entityFileName}-to.model.ts"),
                fileContent = AngularFrontendModelToTemplate.fillTemplate(angularModelClass),
            )

            // create angular service
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("${angularModelClass.entityFileName}/api/${angularModelClass.entityFileName}-api.service.ts"),
                fileContent = AngularFrontendServiceToTemplate.fillTemplate(angularModelClass),
            )

            // create angular create instruction
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("${angularModelClass.entityFileName}/api/create-${angularModelClass.entityFileName}-instruction-to.model.ts"),
                fileContent = AngularFrontendCreateInstructionToTemplate.fillTemplate(angularModelClass),
            )
            // create angular update instruction
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("${angularModelClass.entityFileName}/api/update-${angularModelClass.entityFileName}-instruction-to.model.ts"),
                fileContent = AngularFrontendUpdateInstructionToTemplate.fillTemplate(angularModelClass),
            )
            // create angular delete instruction
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("${angularModelClass.entityFileName}/api/delete-${angularModelClass.entityFileName}-instruction-to.model.ts"),
                fileContent = AngularFrontendDeleteInstructionToTemplate.fillTemplate(angularModelClass),
            )


            // create angular panel view
            val angularPanelViewComponentPath = "${angularModelClass.entityFileName}/component/${angularModelClass.entityFileName}-panel-view/${angularModelClass.entityFileName}-panel-view.component"
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("$angularPanelViewComponentPath.html"),
                fileContent = AngularFrontendPanelViewHtmlTemplate.fillTemplate(angularModelClass),
            )
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("$angularPanelViewComponentPath.scss"),
                fileContent = AngularFrontendPanelViewScssTemplate.fillTemplate(angularModelClass),
            )
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("$angularPanelViewComponentPath.ts"),
                fileContent = AngularFrontendPanelViewTypescriptTemplate.fillTemplate(angularModelClass),
            )

            // create angular table view
            val angularTableViewComponentPath = "${angularModelClass.entityFileName}/component/${angularModelClass.entityFileName}-table-view/${angularModelClass.entityFileName}-table-view.component"
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("$angularTableViewComponentPath.html"),
                fileContent = AngularFrontendTableViewHtmlTemplate.fillTemplate(angularModelClass),
            )
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("$angularTableViewComponentPath.scss"),
                fileContent = AngularFrontendTableViewScssTemplate.fillTemplate(angularModelClass),
            )
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("$angularTableViewComponentPath.ts"),
                fileContent = AngularFrontendTableViewTypescriptTemplate.fillTemplate(angularModelClass),
            )

            // create angular edit view
            val angularEditViewComponentPath = "${angularModelClass.entityFileName}/component/${angularModelClass.entityFileName}-edit-view/${angularModelClass.entityFileName}-edit-view.component"
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("$angularEditViewComponentPath.html"),
                fileContent = AngularFrontendEditViewHtmlTemplate.fillTemplate(angularModelClass),
            )
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("$angularEditViewComponentPath.scss"),
                fileContent = AngularFrontendEditViewScssTemplate.fillTemplate(angularModelClass),
            )
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("$angularEditViewComponentPath.ts"),
                fileContent = AngularFrontendEditViewTypescriptTemplate.fillTemplate(angularModelClass),
            )

            // create angular add view
            val angularAddViewComponentPath = "${angularModelClass.entityFileName}/component/${angularModelClass.entityFileName}-add-view/${angularModelClass.entityFileName}-add-view.component"
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("$angularAddViewComponentPath.html"),
                fileContent = AngularFrontendAddViewHtmlTemplate.fillTemplate(angularModelClass),
            )
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("$angularAddViewComponentPath.scss"),
                fileContent = AngularFrontendAddViewScssTemplate.fillTemplate(angularModelClass),
            )
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("$angularAddViewComponentPath.ts"),
                fileContent = AngularFrontendAddViewTypescriptTemplate.fillTemplate(angularModelClass),
            )

        }
    }
}
