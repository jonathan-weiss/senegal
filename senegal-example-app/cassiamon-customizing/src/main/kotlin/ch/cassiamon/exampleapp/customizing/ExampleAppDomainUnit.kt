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
import ch.cassiamon.exampleapp.customizing.templates.angular.AngularModelField
import ch.cassiamon.exampleapp.customizing.templates.angular.components.formview.textformfield.AngularFrontendTextFormFieldHtmlTemplate
import ch.cassiamon.exampleapp.customizing.templates.angular.components.formview.textformfield.AngularFrontendTextFormFieldScssTemplate
import ch.cassiamon.exampleapp.customizing.templates.angular.components.formview.textformfield.AngularFrontendTextFormFieldTypescriptTemplate
import ch.cassiamon.exampleapp.customizing.templates.angular.api.*
import ch.cassiamon.exampleapp.customizing.templates.angular.components.formview.AngularFrontendFormServiceTemplate
import ch.cassiamon.exampleapp.customizing.templates.angular.components.formview.AngularFrontendFormViewHtmlTemplate
import ch.cassiamon.exampleapp.customizing.templates.angular.components.formview.AngularFrontendFormViewScssTemplate
import ch.cassiamon.exampleapp.customizing.templates.angular.components.formview.AngularFrontendFormViewTypescriptTemplate
import ch.cassiamon.exampleapp.customizing.templates.angular.components.formview.idformfield.AngularFrontendIdFormFieldHtmlTemplate
import ch.cassiamon.exampleapp.customizing.templates.angular.components.formview.idformfield.AngularFrontendIdFormFieldScssTemplate
import ch.cassiamon.exampleapp.customizing.templates.angular.components.formview.idformfield.AngularFrontendIdFormFieldTypescriptTemplate
import ch.cassiamon.exampleapp.customizing.templates.angular.components.searchview.AngularFrontendSearchViewHtmlTemplate
import ch.cassiamon.exampleapp.customizing.templates.angular.components.searchview.AngularFrontendSearchViewScssTemplate
import ch.cassiamon.exampleapp.customizing.templates.angular.components.searchview.AngularFrontendSearchViewTypescriptTemplate
import ch.cassiamon.exampleapp.customizing.templates.angular.service.AngularFrontendServiceTemplate
import ch.cassiamon.exampleapp.customizing.templates.angular.components.tableview.AngularFrontendTableViewHtmlTemplate
import ch.cassiamon.exampleapp.customizing.templates.angular.components.tableview.AngularFrontendTableViewScssTemplate
import ch.cassiamon.exampleapp.customizing.templates.angular.components.tableview.AngularFrontendTableViewTypescriptTemplate
import ch.cassiamon.exampleapp.customizing.templates.angular.service.AngularFrontendModuleTemplate
import ch.cassiamon.exampleapp.customizing.templates.angular.stackcomponents.AngularFrontendStackKeyTypescriptTemplate
import ch.cassiamon.exampleapp.customizing.templates.angular.stackcomponents.entrypoint.AngularFrontendEntryPointHtmlTemplate
import ch.cassiamon.exampleapp.customizing.templates.angular.stackcomponents.entrypoint.AngularFrontendEntryPointScssTemplate
import ch.cassiamon.exampleapp.customizing.templates.angular.stackcomponents.entrypoint.AngularFrontendEntryPointTypescriptTemplate
import ch.cassiamon.exampleapp.customizing.templates.angular.stackcomponents.form.AngularFrontendFormStackEntryHtmlTemplate
import ch.cassiamon.exampleapp.customizing.templates.angular.stackcomponents.form.AngularFrontendFormStackEntryScssTemplate
import ch.cassiamon.exampleapp.customizing.templates.angular.stackcomponents.form.AngularFrontendFormStackEntryTypescriptTemplate
import ch.cassiamon.exampleapp.customizing.templates.angular.stackcomponents.search.AngularFrontendSearchStackEntryHtmlTemplate
import ch.cassiamon.exampleapp.customizing.templates.angular.stackcomponents.search.AngularFrontendSearchStackEntryScssTemplate
import ch.cassiamon.exampleapp.customizing.templates.angular.stackcomponents.search.AngularFrontendSearchStackEntryTypescriptTemplate
import ch.cassiamon.exampleapp.customizing.templates.db.DbTable
import ch.cassiamon.exampleapp.customizing.templates.db.JooqDslTemplate
import ch.cassiamon.exampleapp.customizing.templates.db.JooqRepositoryImplTemplate
import ch.cassiamon.exampleapp.customizing.templates.db.LiquibaseTemplate
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
            // create JOOQ entity file per DB table
            targetFilesCollector.addFile(
                targetFile = toTargetFilePath(persistenceSourceDirectory, dbTable.jooqDslPackage, dbTable.jooqDslFileName),
                fileContent = JooqDslTemplate.fillTemplate(dbTable),
            )

            // create JOOQ repository file per DB table
            targetFilesCollector.addFile(
                targetFile = toTargetFilePath(persistenceSourceDirectory, dbTable.jooqDslPackage, dbTable.jooqRepositoryImplementationFileName),
                fileContent = JooqRepositoryImplTemplate.fillTemplate(dbTable),
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

            // create kotlin model search instruction class
            targetFilesCollector.addFile(
                targetFile = toTargetFilePath(domainSourceDirectory, kotlinModelClass.kotlinPackage, "Search${kotlinModelClass.kotlinClassName}Instruction.kt"),
                fileContent = KotlinModelSearchInstructionTemplate.fillTemplate(kotlinModelClass),
            )


            // create kotlin model service class
            targetFilesCollector.addFile(
                targetFile = toTargetFilePath(domainSourceDirectory, kotlinModelClass.kotlinPackage, "${kotlinModelClass.kotlinClassName}Service.kt"),
                fileContent = KotlinModelServiceTemplate.fillTemplate(kotlinModelClass),
            )

            // create kotlin model reference fields summary classes
            kotlinModelClass.referencingFields().forEach { referencingField ->
                val kotlinModelClassOfReferencedField = referencingField.kotlinModelClass

                targetFilesCollector.addFile(
                    targetFile = toTargetFilePath(domainSourceDirectory, kotlinModelClass.kotlinPackage, "${kotlinModelClass.kotlinClassName}${kotlinModelClassOfReferencedField.kotlinClassName}Summary.kt"),
                    fileContent = KotlinModelReferencedSummaryClassTemplate.fillTemplate(kotlinModelClass = kotlinModelClass, kotlinModelReferencedClass = kotlinModelClassOfReferencedField),
                )

            }

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
                targetFile = toTargetFilePath(frontendApiSourceDirectory, restModelClass.transferObjectPackageName, "Delete${restModelClass.transferObjectBaseName}InstructionTO.kt"),
                fileContent = RestApiTransferObjectDeleteInstructionTemplate.fillTemplate(restModelClass),
            )

            // create REST api transfer object search instruction
            targetFilesCollector.addFile(
                targetFile = toTargetFilePath(frontendApiSourceDirectory, restModelClass.transferObjectPackageName, "Search${restModelClass.transferObjectBaseName}InstructionTO.kt"),
                fileContent = RestApiTransferObjectSearchInstructionTemplate.fillTemplate(restModelClass),
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
            // create angular id model
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("${angularModelClass.entityFileName}/api/${angularModelClass.entityFileName}-id-to.model.ts"),
                fileContent = AngularFrontendIdModelToTemplate.fillTemplate(angularModelClass),
            )
            // create angular model
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("${angularModelClass.entityFileName}/api/${angularModelClass.entityFileName}-to.model.ts"),
                fileContent = AngularFrontendModelToTemplate.fillTemplate(angularModelClass),
            )

            // create angular API service
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("${angularModelClass.entityFileName}/api/${angularModelClass.entityFileName}-api.service.ts"),
                fileContent = AngularFrontendApiServiceTemplate.fillTemplate(angularModelClass),
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
            // create angular search instruction
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("${angularModelClass.entityFileName}/api/search-${angularModelClass.entityFileName}-instruction-to.model.ts"),
                fileContent = AngularFrontendSearchInstructionToTemplate.fillTemplate(angularModelClass),
            )

            // create angular service
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("${angularModelClass.entityFileName}/${angularModelClass.entityFileName}.service.ts"),
                fileContent = AngularFrontendServiceTemplate.fillTemplate(angularModelClass),
            )

            // create angular module
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("${angularModelClass.entityFileName}/${angularModelClass.entityFileName}.module.ts"),
                fileContent = AngularFrontendModuleTemplate.fillTemplate(angularModelClass),
            )


            // create angular search view
            val angularSearchViewComponentPath = "${angularModelClass.entityFileName}/components/${angularModelClass.entityFileName}-search-view/${angularModelClass.entityFileName}-search-view.component"
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("$angularSearchViewComponentPath.html"),
                fileContent = AngularFrontendSearchViewHtmlTemplate.fillTemplate(angularModelClass),
            )
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("$angularSearchViewComponentPath.scss"),
                fileContent = AngularFrontendSearchViewScssTemplate.fillTemplate(angularModelClass),
            )
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("$angularSearchViewComponentPath.ts"),
                fileContent = AngularFrontendSearchViewTypescriptTemplate.fillTemplate(angularModelClass),
            )

            // create angular table view
            val angularTableViewComponentPath = "${angularModelClass.entityFileName}/components/${angularModelClass.entityFileName}-table-view/${angularModelClass.entityFileName}-table-view.component"
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

            // create angular form service
            val angularFormViewPath = "${angularModelClass.entityFileName}/components/${angularModelClass.entityFileName}-form-view"
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("$angularFormViewPath/${angularModelClass.entityFileName}-form.service.ts"),
                fileContent = AngularFrontendFormServiceTemplate.fillTemplate(angularModelClass),
            )

            // create angular form view
            val angularFormViewComponentPath = "$angularFormViewPath/${angularModelClass.entityFileName}-form-view.component"
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("$angularFormViewComponentPath.html"),
                fileContent = AngularFrontendFormViewHtmlTemplate.fillTemplate(angularModelClass),
            )
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("$angularFormViewComponentPath.scss"),
                fileContent = AngularFrontendFormViewScssTemplate.fillTemplate(angularModelClass),
            )
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("$angularFormViewComponentPath.ts"),
                fileContent = AngularFrontendFormViewTypescriptTemplate.fillTemplate(angularModelClass),
            )

            // create angular id form field
            val angularIdFormFieldComponentPath = "${angularModelClass.entityFileName}/components/${angularModelClass.entityFileName}-form-view/${angularModelClass.transferObjectIdFieldFileName}-form-field/${angularModelClass.transferObjectIdFieldFileName}-form-field.component"
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("$angularIdFormFieldComponentPath.html"),
                fileContent = AngularFrontendIdFormFieldHtmlTemplate.fillTemplate(angularModelClass),
            )
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("$angularIdFormFieldComponentPath.scss"),
                fileContent = AngularFrontendIdFormFieldScssTemplate.fillTemplate(angularModelClass),
            )
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("$angularIdFormFieldComponentPath.ts"),
                fileContent = AngularFrontendIdFormFieldTypescriptTemplate.fillTemplate(angularModelClass),
            )


            // create angular form fields
            angularModelClass.angularFields().forEach { angularModelField: AngularModelField ->
                val angularFormFieldComponentPath = "${angularModelClass.entityFileName}/components/${angularModelClass.entityFileName}-form-view/${angularModelClass.entityFileName}-${angularModelField.fieldFileName}-form-field/${angularModelClass.entityFileName}-${angularModelField.fieldFileName}-form-field.component"
                targetFilesCollector.addFile(
                    targetFile = frontendSourceDirectory.resolve("$angularFormFieldComponentPath.html"),
                    fileContent = AngularFrontendTextFormFieldHtmlTemplate.fillTemplate(angularModelClass, angularModelField),
                )
                targetFilesCollector.addFile(
                    targetFile = frontendSourceDirectory.resolve("$angularFormFieldComponentPath.scss"),
                    fileContent = AngularFrontendTextFormFieldScssTemplate.fillTemplate(angularModelClass, angularModelField),
                )
                targetFilesCollector.addFile(
                    targetFile = frontendSourceDirectory.resolve("$angularFormFieldComponentPath.ts"),
                    fileContent = AngularFrontendTextFormFieldTypescriptTemplate.fillTemplate(angularModelClass, angularModelField),
                )
            }

            // create angular entry point view
            val angularStackComponentPath = "${angularModelClass.entityFileName}/stack-components"
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("$angularStackComponentPath/${angularModelClass.entityFileName}-stack-key.ts"),
                fileContent = AngularFrontendStackKeyTypescriptTemplate.fillTemplate(angularModelClass),
            )

            val angularEntryPointComponentPath = "${angularStackComponentPath}/${angularModelClass.entityFileName}-entry-point/${angularModelClass.entityFileName}-entry-point.component"
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("$angularEntryPointComponentPath.html"),
                fileContent = AngularFrontendEntryPointHtmlTemplate.fillTemplate(angularModelClass),
            )
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("$angularEntryPointComponentPath.scss"),
                fileContent = AngularFrontendEntryPointScssTemplate.fillTemplate(angularModelClass),
            )
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("$angularEntryPointComponentPath.ts"),
                fileContent = AngularFrontendEntryPointTypescriptTemplate.fillTemplate(angularModelClass),
            )

            // create angular form stack entry
            val angularFormStackEntryComponentPath = "${angularStackComponentPath}/${angularModelClass.entityFileName}-form-stack-entry/${angularModelClass.entityFileName}-form-stack-entry.component"
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("$angularFormStackEntryComponentPath.html"),
                fileContent = AngularFrontendFormStackEntryHtmlTemplate.fillTemplate(angularModelClass),
            )
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("$angularFormStackEntryComponentPath.scss"),
                fileContent = AngularFrontendFormStackEntryScssTemplate.fillTemplate(angularModelClass),
            )
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("$angularFormStackEntryComponentPath.ts"),
                fileContent = AngularFrontendFormStackEntryTypescriptTemplate.fillTemplate(angularModelClass),
            )

            // create angular search stack entry
            val angularSearchStackEntryComponentPath = "${angularStackComponentPath}/${angularModelClass.entityFileName}-search-stack-entry/${angularModelClass.entityFileName}-search-stack-entry.component"
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("$angularSearchStackEntryComponentPath.html"),
                fileContent = AngularFrontendSearchStackEntryHtmlTemplate.fillTemplate(angularModelClass),
            )
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("$angularSearchStackEntryComponentPath.scss"),
                fileContent = AngularFrontendSearchStackEntryScssTemplate.fillTemplate(angularModelClass),
            )
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("$angularSearchStackEntryComponentPath.ts"),
                fileContent = AngularFrontendSearchStackEntryTypescriptTemplate.fillTemplate(angularModelClass),
            )


        }
    }
}
