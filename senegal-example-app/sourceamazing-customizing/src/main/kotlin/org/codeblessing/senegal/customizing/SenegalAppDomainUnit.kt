package org.codeblessing.senegal.customizing

import org.codeblessing.sourceamazing.api.extensions.ExtensionName
import org.codeblessing.sourceamazing.api.parameter.ParameterAccess
import org.codeblessing.sourceamazing.api.process.DomainUnit
import org.codeblessing.sourceamazing.api.process.datacollection.defaults.DefaultConceptDataCollector
import org.codeblessing.sourceamazing.api.process.datacollection.extensions.DataCollectionExtensionAccess
import org.codeblessing.sourceamazing.api.process.templating.TargetFilesCollector
import java.nio.file.Path

class SenegalAppDomainUnit: DomainUnit<org.codeblessing.senegal.customizing.templates.EntitiesSchema, DefaultConceptDataCollector>(
    schemaDefinitionClass = org.codeblessing.senegal.customizing.templates.EntitiesSchema::class.java,
    inputDefinitionClass = DefaultConceptDataCollector::class.java,
) {

    override fun collectInputData(
        parameterAccess: ParameterAccess,
        extensionAccess: DataCollectionExtensionAccess,
        dataCollector: DefaultConceptDataCollector
    ) {
        val xmlDefinitionFile = org.codeblessing.senegal.customizing.SenegalAppParameters.xmlDefinitionFile(parameterAccess)
        extensionAccess.collectWithDataCollectionFromFilesExtension(
            extensionName = ExtensionName.of("XmlSchemaInputExtension"),
            inputFiles = setOf(xmlDefinitionFile),
        )
    }

    private fun toTargetFilePath(basePath: Path, packageName: String, fileName: String): Path {
        return basePath.resolve(packageName.replace(".", "/")).resolve(fileName)
    }

    override fun collectTargetFiles(
        parameterAccess: ParameterAccess,
        schemaInstance: org.codeblessing.senegal.customizing.templates.EntitiesSchema,
        targetFilesCollector: TargetFilesCollector
    ) {
        val outputDirectory = org.codeblessing.senegal.customizing.SenegalAppParameters.outputDirectory(parameterAccess)
        val liquibaseResourceDirectory = org.codeblessing.senegal.customizing.SenegalAppParameters.persistenceResourcePath(parameterAccess).resolve("db/changelog/")
        val persistenceSourceDirectory = org.codeblessing.senegal.customizing.SenegalAppParameters.persistencePath(parameterAccess)
        val domainSourceDirectory = org.codeblessing.senegal.customizing.SenegalAppParameters.domainPath(parameterAccess)
        val sharedDomainSourceDirectory = org.codeblessing.senegal.customizing.SenegalAppParameters.sharedDomainPath(parameterAccess)
        val frontendApiSourceDirectory = org.codeblessing.senegal.customizing.SenegalAppParameters.frontendApiPath(parameterAccess)
        val frontendSourceDirectory = org.codeblessing.senegal.customizing.SenegalAppParameters.frontendPath(parameterAccess)

        // create description file per entity
        schemaInstance.entities().forEach {
            val descriptionFilePath = outputDirectory.resolve("${it.getName()}.description.txt")
            targetFilesCollector.addFile(
                targetFile = descriptionFilePath,
                fileContent = org.codeblessing.senegal.customizing.templates.EntityDescriptionTemplate.createEntityDescriptionTemplate(descriptionFilePath, it)
            )
        }

        // create description file index
        val descriptionFilePath = outputDirectory.resolve("_index.description.txt")
        targetFilesCollector.addFile(
            targetFile = descriptionFilePath,
            fileContent = org.codeblessing.senegal.customizing.templates.EntityDescriptionTemplate.createEntitiesDescriptionTemplate(descriptionFilePath, schemaInstance.entities())
        )

        // create liquibase file per DB table
        schemaInstance.entities().map { org.codeblessing.senegal.customizing.templates.db.DbTable(it) }.forEach { dbTable ->
            targetFilesCollector.addFile(
                targetFile = liquibaseResourceDirectory.resolve(dbTable.liquibaseFileName),
                fileContent = org.codeblessing.senegal.customizing.templates.db.LiquibaseTemplate.createLiquibaseXmlFileTemplate(dbTable),
            )
        }

        // create liquibase index
        targetFilesCollector.addFile(
            targetFile = liquibaseResourceDirectory.resolve(org.codeblessing.senegal.customizing.templates.db.DbTable.liquibaseIndexFileName),
            fileContent = org.codeblessing.senegal.customizing.templates.db.LiquibaseTemplate.createLiquibaseXmlIndexFileTemplate(schemaInstance.entities().map {
                org.codeblessing.senegal.customizing.templates.db.DbTable(it)
            }),
        )

        schemaInstance.entities().map { org.codeblessing.senegal.customizing.templates.db.DbTable(it) }.forEach { dbTable ->
            // create JOOQ entity file per DB table
            targetFilesCollector.addFile(
                targetFile = toTargetFilePath(persistenceSourceDirectory, dbTable.jooqDslPackage, dbTable.jooqDslFileName),
                fileContent = org.codeblessing.senegal.customizing.templates.db.JooqDslTemplate.fillTemplate(dbTable),
            )

            // create JOOQ repository file per DB table
            targetFilesCollector.addFile(
                targetFile = toTargetFilePath(persistenceSourceDirectory, dbTable.jooqDslPackage, dbTable.jooqRepositoryImplementationFileName),
                fileContent = org.codeblessing.senegal.customizing.templates.db.JooqRepositoryImplTemplate.fillTemplate(dbTable),
            )
        }

        schemaInstance.entities().map { org.codeblessing.senegal.customizing.templates.kotlinmodel.KotlinModelClass(it) }.forEach { kotlinModelClass ->
            // create kotlin model class
            targetFilesCollector.addFile(
                targetFile = toTargetFilePath(domainSourceDirectory, kotlinModelClass.kotlinPackage, "${kotlinModelClass.kotlinClassName}.kt"),
                fileContent = org.codeblessing.senegal.customizing.templates.kotlinmodel.KotlinModelClassTemplate.fillTemplate(kotlinModelClass),
            )

            // create kotlin model id class
            targetFilesCollector.addFile(
                targetFile = toTargetFilePath(domainSourceDirectory, kotlinModelClass.kotlinPackage, "${kotlinModelClass.kotlinClassName}Id.kt"),
                fileContent = org.codeblessing.senegal.customizing.templates.kotlinmodel.KotlinModelIdClassTemplate.fillTemplate(kotlinModelClass),
            )

            // create kotlin model repository class
            targetFilesCollector.addFile(
                targetFile = toTargetFilePath(domainSourceDirectory, kotlinModelClass.kotlinPackage, "${kotlinModelClass.kotlinClassName}Repository.kt"),
                fileContent = org.codeblessing.senegal.customizing.templates.kotlinmodel.KotlinModelRepositoryTemplate.fillTemplate(kotlinModelClass),
            )

            // create kotlin model create instruction class
            targetFilesCollector.addFile(
                targetFile = toTargetFilePath(domainSourceDirectory, kotlinModelClass.kotlinPackage, "Create${kotlinModelClass.kotlinClassName}Instruction.kt"),
                fileContent = org.codeblessing.senegal.customizing.templates.kotlinmodel.KotlinModelCreateInstructionTemplate.fillTemplate(kotlinModelClass),
            )

            // create kotlin model update instruction class
            targetFilesCollector.addFile(
                targetFile = toTargetFilePath(domainSourceDirectory, kotlinModelClass.kotlinPackage, "Update${kotlinModelClass.kotlinClassName}Instruction.kt"),
                fileContent = org.codeblessing.senegal.customizing.templates.kotlinmodel.KotlinModelUpdateInstructionTemplate.fillTemplate(kotlinModelClass),
            )

            // create kotlin model create delete instruction class
            targetFilesCollector.addFile(
                targetFile = toTargetFilePath(domainSourceDirectory, kotlinModelClass.kotlinPackage, "Delete${kotlinModelClass.kotlinClassName}Instruction.kt"),
                fileContent = org.codeblessing.senegal.customizing.templates.kotlinmodel.KotlinModelDeleteInstructionTemplate.fillTemplate(kotlinModelClass),
            )

            // create kotlin model search instruction class
            targetFilesCollector.addFile(
                targetFile = toTargetFilePath(domainSourceDirectory, kotlinModelClass.kotlinPackage, "Search${kotlinModelClass.kotlinClassName}Instruction.kt"),
                fileContent = org.codeblessing.senegal.customizing.templates.kotlinmodel.KotlinModelSearchInstructionTemplate.fillTemplate(kotlinModelClass),
            )


            // create kotlin model service class
            targetFilesCollector.addFile(
                targetFile = toTargetFilePath(domainSourceDirectory, kotlinModelClass.kotlinPackage, "${kotlinModelClass.kotlinClassName}Service.kt"),
                fileContent = org.codeblessing.senegal.customizing.templates.kotlinmodel.KotlinModelServiceTemplate.fillTemplate(kotlinModelClass),
            )

            // create kotlin model reference fields summary classes
            kotlinModelClass.referencingFields().forEach { referencingField ->
                val kotlinModelClassOfReferencedField = referencingField.kotlinModelClass

                targetFilesCollector.addFile(
                    targetFile = toTargetFilePath(domainSourceDirectory, kotlinModelClass.kotlinPackage, "${kotlinModelClass.kotlinClassName}${kotlinModelClassOfReferencedField.kotlinClassName}Summary.kt"),
                    fileContent = org.codeblessing.senegal.customizing.templates.kotlinmodel.KotlinModelReferencedSummaryClassTemplate.fillTemplate(kotlinModelClass = kotlinModelClass, kotlinModelReferencedClass = kotlinModelClassOfReferencedField),
                )

            }

        }

        schemaInstance.entities().map { org.codeblessing.senegal.customizing.templates.restapi.RestModelClass(it) }.forEach { restModelClass ->
            // create REST api facade class
            targetFilesCollector.addFile(
                targetFile = toTargetFilePath(frontendApiSourceDirectory, restModelClass.facadePackageName, "${restModelClass.facadeClassName}.kt"),
                fileContent = org.codeblessing.senegal.customizing.templates.restapi.RestApiFacadeTemplate.fillTemplate(restModelClass),
            )

            // create REST api transfer object
            targetFilesCollector.addFile(
                targetFile = toTargetFilePath(frontendApiSourceDirectory, restModelClass.transferObjectPackageName, "${restModelClass.transferObjectBaseName}TO.kt"),
                fileContent = org.codeblessing.senegal.customizing.templates.restapi.RestApiTransferObjectTemplate.fillTemplate(restModelClass),
            )

            // create REST api transfer object create instruction
            targetFilesCollector.addFile(
                targetFile = toTargetFilePath(frontendApiSourceDirectory, restModelClass.transferObjectPackageName, "Create${restModelClass.transferObjectBaseName}InstructionTO.kt"),
                fileContent = org.codeblessing.senegal.customizing.templates.restapi.RestApiTransferObjectCreateInstructionTemplate.fillTemplate(restModelClass),
            )

            // create REST api transfer object update instruction
            targetFilesCollector.addFile(
                targetFile = toTargetFilePath(frontendApiSourceDirectory, restModelClass.transferObjectPackageName, "Update${restModelClass.transferObjectBaseName}InstructionTO.kt"),
                fileContent = org.codeblessing.senegal.customizing.templates.restapi.RestApiTransferObjectUpdateInstructionTemplate.fillTemplate(restModelClass),
            )

            // create REST api transfer object delete instruction
            targetFilesCollector.addFile(
                targetFile = toTargetFilePath(frontendApiSourceDirectory, restModelClass.transferObjectPackageName, "Delete${restModelClass.transferObjectBaseName}InstructionTO.kt"),
                fileContent = org.codeblessing.senegal.customizing.templates.restapi.RestApiTransferObjectDeleteInstructionTemplate.fillTemplate(restModelClass),
            )

            // create REST api transfer object search instruction
            targetFilesCollector.addFile(
                targetFile = toTargetFilePath(frontendApiSourceDirectory, restModelClass.transferObjectPackageName, "Search${restModelClass.transferObjectBaseName}InstructionTO.kt"),
                fileContent = org.codeblessing.senegal.customizing.templates.restapi.RestApiTransferObjectSearchInstructionTemplate.fillTemplate(restModelClass),
            )


            // create REST api controller
            targetFilesCollector.addFile(
                targetFile = toTargetFilePath(frontendApiSourceDirectory, restModelClass.controllerPackageName, "${restModelClass.controllerClassName}.kt"),
                fileContent = org.codeblessing.senegal.customizing.templates.restapi.RestApiControllerTemplate.fillTemplate(restModelClass),
            )
        }

        // create angular modules file
        targetFilesCollector.addFile(
            targetFile = frontendSourceDirectory.resolve("generated-entities.module.ts"),
            fileContent = org.codeblessing.senegal.customizing.templates.angular.AngularFrontendModulesTemplate.fillTemplate(schemaInstance.entities().map {
                org.codeblessing.senegal.customizing.templates.angular.AngularModelClass(it)
            }),
        )

        // create angular routing file
        targetFilesCollector.addFile(
            targetFile = frontendSourceDirectory.resolve("generated-entities-routing.module.ts"),
            fileContent = org.codeblessing.senegal.customizing.templates.angular.AngularFrontendRoutingTemplate.fillTemplate(schemaInstance.entities().map {
                org.codeblessing.senegal.customizing.templates.angular.AngularModelClass(it)
            }),
        )

        schemaInstance.entities().map { org.codeblessing.senegal.customizing.templates.angular.AngularModelClass(it) }.forEach { angularModelClass ->
            // create angular id model
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("${angularModelClass.entityFileName}/api/${angularModelClass.entityFileName}-id-to.model.ts"),
                fileContent = org.codeblessing.senegal.customizing.templates.angular.api.AngularFrontendIdModelToTemplate.fillTemplate(angularModelClass),
            )
            // create angular model
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("${angularModelClass.entityFileName}/api/${angularModelClass.entityFileName}-to.model.ts"),
                fileContent = org.codeblessing.senegal.customizing.templates.angular.api.AngularFrontendModelToTemplate.fillTemplate(angularModelClass),
            )

            // create angular API service
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("${angularModelClass.entityFileName}/api/${angularModelClass.entityFileName}-api.service.ts"),
                fileContent = org.codeblessing.senegal.customizing.templates.angular.api.AngularFrontendApiServiceTemplate.fillTemplate(angularModelClass),
            )

            // create angular create instruction
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("${angularModelClass.entityFileName}/api/create-${angularModelClass.entityFileName}-instruction-to.model.ts"),
                fileContent = org.codeblessing.senegal.customizing.templates.angular.api.AngularFrontendCreateInstructionToTemplate.fillTemplate(angularModelClass),
            )
            // create angular update instruction
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("${angularModelClass.entityFileName}/api/update-${angularModelClass.entityFileName}-instruction-to.model.ts"),
                fileContent = org.codeblessing.senegal.customizing.templates.angular.api.AngularFrontendUpdateInstructionToTemplate.fillTemplate(angularModelClass),
            )
            // create angular delete instruction
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("${angularModelClass.entityFileName}/api/delete-${angularModelClass.entityFileName}-instruction-to.model.ts"),
                fileContent = org.codeblessing.senegal.customizing.templates.angular.api.AngularFrontendDeleteInstructionToTemplate.fillTemplate(angularModelClass),
            )
            // create angular search instruction
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("${angularModelClass.entityFileName}/api/search-${angularModelClass.entityFileName}-instruction-to.model.ts"),
                fileContent = org.codeblessing.senegal.customizing.templates.angular.api.AngularFrontendSearchInstructionToTemplate.fillTemplate(angularModelClass),
            )

            // create angular service
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("${angularModelClass.entityFileName}/${angularModelClass.entityFileName}.service.ts"),
                fileContent = org.codeblessing.senegal.customizing.templates.angular.service.AngularFrontendServiceTemplate.fillTemplate(angularModelClass),
            )

            // create angular module
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("${angularModelClass.entityFileName}/${angularModelClass.entityFileName}.module.ts"),
                fileContent = org.codeblessing.senegal.customizing.templates.angular.service.AngularFrontendModuleTemplate.fillTemplate(angularModelClass),
            )


            // create angular search view
            val angularSearchViewComponentPath = "${angularModelClass.entityFileName}/components/${angularModelClass.entityFileName}-search-view/${angularModelClass.entityFileName}-search-view.component"
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("$angularSearchViewComponentPath.html"),
                fileContent = org.codeblessing.senegal.customizing.templates.angular.components.searchview.AngularFrontendSearchViewHtmlTemplate.fillTemplate(angularModelClass),
            )
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("$angularSearchViewComponentPath.scss"),
                fileContent = org.codeblessing.senegal.customizing.templates.angular.components.searchview.AngularFrontendSearchViewScssTemplate.fillTemplate(angularModelClass),
            )
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("$angularSearchViewComponentPath.ts"),
                fileContent = org.codeblessing.senegal.customizing.templates.angular.components.searchview.AngularFrontendSearchViewTypescriptTemplate.fillTemplate(angularModelClass),
            )

            // create angular table view
            val angularTableViewComponentPath = "${angularModelClass.entityFileName}/components/${angularModelClass.entityFileName}-table-view/${angularModelClass.entityFileName}-table-view.component"
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("$angularTableViewComponentPath.html"),
                fileContent = org.codeblessing.senegal.customizing.templates.angular.components.tableview.AngularFrontendTableViewHtmlTemplate.fillTemplate(angularModelClass),
            )
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("$angularTableViewComponentPath.scss"),
                fileContent = org.codeblessing.senegal.customizing.templates.angular.components.tableview.AngularFrontendTableViewScssTemplate.fillTemplate(angularModelClass),
            )
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("$angularTableViewComponentPath.ts"),
                fileContent = org.codeblessing.senegal.customizing.templates.angular.components.tableview.AngularFrontendTableViewTypescriptTemplate.fillTemplate(angularModelClass),
            )

            // create angular form service
            val angularFormViewPath = "${angularModelClass.entityFileName}/components/${angularModelClass.entityFileName}-form-view"
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("$angularFormViewPath/${angularModelClass.entityFileName}-form.service.ts"),
                fileContent = org.codeblessing.senegal.customizing.templates.angular.components.formview.AngularFrontendFormServiceTemplate.fillTemplate(angularModelClass),
            )

            // create angular form view
            val angularFormViewComponentPath = "$angularFormViewPath/${angularModelClass.entityFileName}-form-view.component"
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("$angularFormViewComponentPath.html"),
                fileContent = org.codeblessing.senegal.customizing.templates.angular.components.formview.AngularFrontendFormViewHtmlTemplate.fillTemplate(angularModelClass),
            )
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("$angularFormViewComponentPath.scss"),
                fileContent = org.codeblessing.senegal.customizing.templates.angular.components.formview.AngularFrontendFormViewScssTemplate.fillTemplate(angularModelClass),
            )
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("$angularFormViewComponentPath.ts"),
                fileContent = org.codeblessing.senegal.customizing.templates.angular.components.formview.AngularFrontendFormViewTypescriptTemplate.fillTemplate(angularModelClass),
            )

            // create angular id form field
            val angularIdFormFieldComponentPath = "${angularModelClass.entityFileName}/components/${angularModelClass.entityFileName}-form-view/${angularModelClass.transferObjectIdFieldFileName}-form-field/${angularModelClass.transferObjectIdFieldFileName}-form-field.component"
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("$angularIdFormFieldComponentPath.html"),
                fileContent = org.codeblessing.senegal.customizing.templates.angular.components.formview.idformfield.AngularFrontendIdFormFieldHtmlTemplate.fillTemplate(angularModelClass),
            )
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("$angularIdFormFieldComponentPath.scss"),
                fileContent = org.codeblessing.senegal.customizing.templates.angular.components.formview.idformfield.AngularFrontendIdFormFieldScssTemplate.fillTemplate(angularModelClass),
            )
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("$angularIdFormFieldComponentPath.ts"),
                fileContent = org.codeblessing.senegal.customizing.templates.angular.components.formview.idformfield.AngularFrontendIdFormFieldTypescriptTemplate.fillTemplate(angularModelClass),
            )


            // create angular form fields
            angularModelClass.angularFields().forEach { angularModelField: org.codeblessing.senegal.customizing.templates.angular.AngularModelField ->
                val angularFormFieldComponentPath = "${angularModelClass.entityFileName}/components/${angularModelClass.entityFileName}-form-view/${angularModelClass.entityFileName}-${angularModelField.fieldFileName}-form-field/${angularModelClass.entityFileName}-${angularModelField.fieldFileName}-form-field.component"
                targetFilesCollector.addFile(
                    targetFile = frontendSourceDirectory.resolve("$angularFormFieldComponentPath.html"),
                    fileContent = org.codeblessing.senegal.customizing.templates.angular.components.formview.textformfield.AngularFrontendTextFormFieldHtmlTemplate.fillTemplate(angularModelClass, angularModelField),
                )
                targetFilesCollector.addFile(
                    targetFile = frontendSourceDirectory.resolve("$angularFormFieldComponentPath.scss"),
                    fileContent = org.codeblessing.senegal.customizing.templates.angular.components.formview.textformfield.AngularFrontendTextFormFieldScssTemplate.fillTemplate(angularModelClass, angularModelField),
                )
                targetFilesCollector.addFile(
                    targetFile = frontendSourceDirectory.resolve("$angularFormFieldComponentPath.ts"),
                    fileContent = org.codeblessing.senegal.customizing.templates.angular.components.formview.textformfield.AngularFrontendTextFormFieldTypescriptTemplate.fillTemplate(angularModelClass, angularModelField),
                )
            }

            // create angular entry point view
            val angularStackComponentPath = "${angularModelClass.entityFileName}/stack-components"
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("$angularStackComponentPath/${angularModelClass.entityFileName}-stack-key.ts"),
                fileContent = org.codeblessing.senegal.customizing.templates.angular.stackcomponents.AngularFrontendStackKeyTypescriptTemplate.fillTemplate(angularModelClass),
            )

            val angularEntryPointComponentPath = "${angularStackComponentPath}/${angularModelClass.entityFileName}-entry-point/${angularModelClass.entityFileName}-entry-point.component"
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("$angularEntryPointComponentPath.html"),
                fileContent = org.codeblessing.senegal.customizing.templates.angular.stackcomponents.entrypoint.AngularFrontendEntryPointHtmlTemplate.fillTemplate(angularModelClass),
            )
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("$angularEntryPointComponentPath.scss"),
                fileContent = org.codeblessing.senegal.customizing.templates.angular.stackcomponents.entrypoint.AngularFrontendEntryPointScssTemplate.fillTemplate(angularModelClass),
            )
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("$angularEntryPointComponentPath.ts"),
                fileContent = org.codeblessing.senegal.customizing.templates.angular.stackcomponents.entrypoint.AngularFrontendEntryPointTypescriptTemplate.fillTemplate(angularModelClass),
            )

            // create angular form stack entry
            val angularFormStackEntryComponentPath = "${angularStackComponentPath}/${angularModelClass.entityFileName}-form-stack-entry/${angularModelClass.entityFileName}-form-stack-entry.component"
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("$angularFormStackEntryComponentPath.html"),
                fileContent = org.codeblessing.senegal.customizing.templates.angular.stackcomponents.form.AngularFrontendFormStackEntryHtmlTemplate.fillTemplate(angularModelClass),
            )
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("$angularFormStackEntryComponentPath.scss"),
                fileContent = org.codeblessing.senegal.customizing.templates.angular.stackcomponents.form.AngularFrontendFormStackEntryScssTemplate.fillTemplate(angularModelClass),
            )
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("$angularFormStackEntryComponentPath.ts"),
                fileContent = org.codeblessing.senegal.customizing.templates.angular.stackcomponents.form.AngularFrontendFormStackEntryTypescriptTemplate.fillTemplate(angularModelClass),
            )

            // create angular search stack entry
            val angularSearchStackEntryComponentPath = "${angularStackComponentPath}/${angularModelClass.entityFileName}-search-stack-entry/${angularModelClass.entityFileName}-search-stack-entry.component"
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("$angularSearchStackEntryComponentPath.html"),
                fileContent = org.codeblessing.senegal.customizing.templates.angular.stackcomponents.search.AngularFrontendSearchStackEntryHtmlTemplate.fillTemplate(angularModelClass),
            )
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("$angularSearchStackEntryComponentPath.scss"),
                fileContent = org.codeblessing.senegal.customizing.templates.angular.stackcomponents.search.AngularFrontendSearchStackEntryScssTemplate.fillTemplate(angularModelClass),
            )
            targetFilesCollector.addFile(
                targetFile = frontendSourceDirectory.resolve("$angularSearchStackEntryComponentPath.ts"),
                fileContent = org.codeblessing.senegal.customizing.templates.angular.stackcomponents.search.AngularFrontendSearchStackEntryTypescriptTemplate.fillTemplate(angularModelClass),
            )


        }
    }
}
