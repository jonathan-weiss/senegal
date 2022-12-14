package ch.senegal.example

import ch.senegal.engine.util.CaseUtil
import ch.senegal.plugin.*
import ch.senegal.plugin.factory.FacetFactory
import ch.senegal.plugin.model.ModelNode
import java.nio.file.Path


object AngularFrontendPurposePlugin : Purpose {
    override val purposeName: PurposeName = PurposeName.of("AngularFrontend")

    val angularFrontendBasePathFacet = FacetFactory.DirectoryFacetFactory.createFacet(
        facetName = FacetName.of("BasePath"),
        enclosingConceptName = EntitiesConceptPlugin.conceptName
    )

    val angularFrontendDecapitalizedEntityNameFacet = FacetFactory.StringFacetFactory.createCalculatedFacet(
        facetName = FacetName.of("DecapitalizedEntityName"),
        enclosingConceptName = EntityConceptPlugin.conceptName
    ) { modelNode: ModelNode -> modelNode
        .getStringFacetValue(EntityPurposePlugin.purposeName, EntityPurposePlugin.entityNameFacet.facetName)
        ?.let { CaseUtil.decapitalize(it) }
    }

    val angularFrontendEntityNameFacet = FacetFactory.StringFacetFactory.createCalculatedFacet(
        facetName = FacetName.of("EntityName"),
        enclosingConceptName = EntityConceptPlugin.conceptName
    ) { modelNode: ModelNode -> modelNode
        .getStringFacetValue(EntityPurposePlugin.purposeName, EntityPurposePlugin.entityNameFacet.facetName)
    }

    val angularFrontendEntityFileNameFacet = FacetFactory.StringFacetFactory.createCalculatedFacet(
        facetName = FacetName.of("EntityFilename"),
        enclosingConceptName = EntityConceptPlugin.conceptName
    ) { modelNode: ModelNode -> modelNode
        .getStringFacetValue(EntityPurposePlugin.purposeName, EntityPurposePlugin.entityNameFacet.facetName)
        ?.let { CaseUtil.camelToDashCase(it) }
    }


    val angularFrontendTransferObjectIdFieldTypeFacet = FacetFactory.StringFacetFactory.createCalculatedFacet(
        facetName = FacetName.of("TransferObjectIdFieldType"),
        enclosingConceptName = EntityConceptPlugin.conceptName
    ) { _: ModelNode ->
        return@createCalculatedFacet "UuidTO"
    }

    val angularFrontendTransferObjectIdFieldNameFacet = FacetFactory.StringFacetFactory.createCalculatedFacet(
        facetName = FacetName.of("TransferObjectIdFieldName"),
        enclosingConceptName = EntityConceptPlugin.conceptName
    ) { modelNode: ModelNode ->
        val entityName = modelNode.getStringFacetValue(EntityPurposePlugin.purposeName, EntityPurposePlugin.entityNameFacet.facetName)
            ?: return@createCalculatedFacet null

        return@createCalculatedFacet CaseUtil.decapitalize("${entityName}Id")
    }

    val angularFrontendTransferObjectFieldNameFacet = FacetFactory.StringFacetFactory.createCalculatedFacet(
        facetName = FacetName.of("TransferObjectFieldName"),
        enclosingConceptName = EntityAttributeConceptPlugin.conceptName
    ) { modelNode: ModelNode -> modelNode
        .getStringFacetValue(EntityPurposePlugin.purposeName, EntityPurposePlugin.entityAttributeNameFacet.facetName)
    }

    private val transferObjectStringType = StringEnumerationFacetOption("string")
    private val transferObjectIntType = StringEnumerationFacetOption("number")
    private val transferObjectBooleanType = StringEnumerationFacetOption("boolean")

    val angularFrontendTransferObjectFieldTypeFacet = FacetFactory.StringEnumerationFacetFactory.createCalculatedFacet(
        facetName = FacetName.of("TransferObjectFieldType"),
        enclosingConceptName = EntityAttributeConceptPlugin.conceptName,
        enumerationOptions = listOf(transferObjectStringType, transferObjectIntType, transferObjectBooleanType)
    ) { modelNode: ModelNode ->


        val entityAttributeTypeOption =  modelNode.getEnumFacetOption(EntityPurposePlugin.purposeName, EntityPurposePlugin.entityAttributeTypeFacet.facetName)
            ?: return@createCalculatedFacet null
        return@createCalculatedFacet when(entityAttributeTypeOption) {
            EntityPurposePlugin.entityAttributeTextType -> transferObjectStringType.name
            EntityPurposePlugin.entityAttributeIntegerNumberType -> transferObjectIntType.name
            EntityPurposePlugin.entityAttributeBooleanType -> transferObjectBooleanType.name
            else -> null
        }

    }

    private val transferObjectStringDefaultValue = StringEnumerationFacetOption("''")
    private val transferObjectIntDefaultValue = StringEnumerationFacetOption("0")
    private val transferObjectBooleanDefaultValue = StringEnumerationFacetOption("false")

    val angularFrontendTransferObjectFieldDefaultValueFacet = FacetFactory.StringEnumerationFacetFactory.createCalculatedFacet(
        facetName = FacetName.of("TransferObjectFieldDefaultValue"),
        enclosingConceptName = EntityAttributeConceptPlugin.conceptName,
        enumerationOptions = listOf(transferObjectStringDefaultValue, transferObjectIntDefaultValue, transferObjectBooleanDefaultValue)
    ) { modelNode: ModelNode ->
        val entityAttributeTypeOption =  modelNode.getEnumFacetOption(EntityPurposePlugin.purposeName, EntityPurposePlugin.entityAttributeTypeFacet.facetName)
            ?: return@createCalculatedFacet null
        return@createCalculatedFacet when(entityAttributeTypeOption) {
            EntityPurposePlugin.entityAttributeTextType -> transferObjectStringDefaultValue.name
            EntityPurposePlugin.entityAttributeIntegerNumberType -> transferObjectIntDefaultValue.name
            EntityPurposePlugin.entityAttributeBooleanType -> transferObjectBooleanDefaultValue.name
            else -> null
        }

    }

    override fun createTemplateTargets(modelNode: ModelNode, defaultOutputPath: Path): Set<TemplateTarget> {

        return when(modelNode.concept().conceptName) {
            EntitiesConceptPlugin.conceptName -> createEntitiesTemplates(modelNode)
            EntityConceptPlugin.conceptName -> createEntityTemplates(modelNode)
            else -> emptySet()
        }
    }
    private fun createEntitiesTemplates(modelNode: ModelNode): Set<TemplateTarget> {
        val targets: MutableSet<TemplateTarget> = mutableSetOf()
        val angularFrontendBasePath = modelNode.getDirectoryFacetValue(purposeName, angularFrontendBasePathFacet.facetName)

        if(angularFrontendBasePath != null) {
            targets.add(TemplateTarget(angularFrontendBasePath.resolve("generated-entities.module.ts"), TemplateForFreemarker("/ch/senegal/pluginexample/angular-frontent-component-generated-entities-module-ts.ftl")))
            targets.add(TemplateTarget(angularFrontendBasePath.resolve("generated-entities-routing.module.ts"), TemplateForFreemarker("/ch/senegal/pluginexample/angular-frontent-component-generated-entities-routing-module-ts.ftl")))
        }

        return targets

    }

    private fun createEntityTemplates(modelNode: ModelNode): Set<TemplateTarget> {
        val targets: MutableSet<TemplateTarget> = mutableSetOf()

        val angularFrontendBasePath = modelNode.parentModelNode()?.getDirectoryFacetValue(purposeName, angularFrontendBasePathFacet.facetName)
        val entityFileName = modelNode.getStringFacetValue(purposeName, angularFrontendEntityFileNameFacet.facetName)

        if(angularFrontendBasePath != null && entityFileName != null) {
            targets.add(TemplateTarget(angularFrontendBasePath.resolve("${entityFileName}/api/${entityFileName}-to.model.ts"), TemplateForFreemarker("/ch/senegal/pluginexample/angular-frontend-transfer-object.ftl")))
            targets.add(TemplateTarget(angularFrontendBasePath.resolve("${entityFileName}/api/create-${entityFileName}-instruction-to.model.ts"), TemplateForFreemarker("/ch/senegal/pluginexample/angular-frontend-transfer-object-create-instruction.ftl")))
            targets.add(TemplateTarget(angularFrontendBasePath.resolve("${entityFileName}/api/update-${entityFileName}-instruction-to.model.ts"), TemplateForFreemarker("/ch/senegal/pluginexample/angular-frontend-transfer-object-update-instruction.ftl")))
            targets.add(TemplateTarget(angularFrontendBasePath.resolve("${entityFileName}/api/delete-${entityFileName}-instruction-to.model.ts"), TemplateForFreemarker("/ch/senegal/pluginexample/angular-frontend-transfer-object-delete-instruction.ftl")))
            targets.add(TemplateTarget(angularFrontendBasePath.resolve("${entityFileName}/api/${entityFileName}-api.service.ts"), TemplateForFreemarker("/ch/senegal/pluginexample/angular-frontend-service.ftl")))
            targets.add(TemplateTarget(angularFrontendBasePath.resolve("${entityFileName}/component/${entityFileName}-panel-view/${entityFileName}-panel-view.component.ts"), TemplateForFreemarker("/ch/senegal/pluginexample/angular-frontent-component-panel-view-ts.ftl")))
            targets.add(TemplateTarget(angularFrontendBasePath.resolve("${entityFileName}/component/${entityFileName}-panel-view/${entityFileName}-panel-view.component.scss"), TemplateForFreemarker("/ch/senegal/pluginexample/angular-frontent-component-panel-view-scss.ftl")))
            targets.add(TemplateTarget(angularFrontendBasePath.resolve("${entityFileName}/component/${entityFileName}-panel-view/${entityFileName}-panel-view.component.html"), TemplateForFreemarker("/ch/senegal/pluginexample/angular-frontent-component-panel-view-html.ftl")))
            targets.add(TemplateTarget(angularFrontendBasePath.resolve("${entityFileName}/component/${entityFileName}-table-view/${entityFileName}-table-view.component.ts"), TemplateForFreemarker("/ch/senegal/pluginexample/angular-frontent-component-table-view-ts.ftl")))
            targets.add(TemplateTarget(angularFrontendBasePath.resolve("${entityFileName}/component/${entityFileName}-table-view/${entityFileName}-table-view.component.scss"), TemplateForFreemarker("/ch/senegal/pluginexample/angular-frontent-component-table-view-scss.ftl")))
            targets.add(TemplateTarget(angularFrontendBasePath.resolve("${entityFileName}/component/${entityFileName}-table-view/${entityFileName}-table-view.component.html"), TemplateForFreemarker("/ch/senegal/pluginexample/angular-frontent-component-table-view-html.ftl")))
            targets.add(TemplateTarget(angularFrontendBasePath.resolve("${entityFileName}/component/${entityFileName}-edit-view/${entityFileName}-edit-view.component.ts"), TemplateForFreemarker("/ch/senegal/pluginexample/angular-frontent-component-edit-view-ts.ftl")))
            targets.add(TemplateTarget(angularFrontendBasePath.resolve("${entityFileName}/component/${entityFileName}-edit-view/${entityFileName}-edit-view.component.scss"), TemplateForFreemarker("/ch/senegal/pluginexample/angular-frontent-component-edit-view-scss.ftl")))
            targets.add(TemplateTarget(angularFrontendBasePath.resolve("${entityFileName}/component/${entityFileName}-edit-view/${entityFileName}-edit-view.component.html"), TemplateForFreemarker("/ch/senegal/pluginexample/angular-frontent-component-edit-view-html.ftl")))
            targets.add(TemplateTarget(angularFrontendBasePath.resolve("${entityFileName}/component/${entityFileName}-add-view/${entityFileName}-add-view.component.ts"), TemplateForFreemarker("/ch/senegal/pluginexample/angular-frontent-component-add-view-ts.ftl")))
            targets.add(TemplateTarget(angularFrontendBasePath.resolve("${entityFileName}/component/${entityFileName}-add-view/${entityFileName}-add-view.component.scss"), TemplateForFreemarker("/ch/senegal/pluginexample/angular-frontent-component-add-view-scss.ftl")))
            targets.add(TemplateTarget(angularFrontendBasePath.resolve("${entityFileName}/component/${entityFileName}-add-view/${entityFileName}-add-view.component.html"), TemplateForFreemarker("/ch/senegal/pluginexample/angular-frontent-component-add-view-html.ftl")))

        }

        return targets
    }

    override val facets: Set<Facet> = setOf(
        angularFrontendBasePathFacet,
        angularFrontendDecapitalizedEntityNameFacet,
        angularFrontendEntityNameFacet,
        angularFrontendEntityFileNameFacet,
        angularFrontendTransferObjectFieldNameFacet,
        angularFrontendTransferObjectFieldTypeFacet,
        angularFrontendTransferObjectIdFieldNameFacet,
        angularFrontendTransferObjectIdFieldTypeFacet,
        angularFrontendTransferObjectFieldDefaultValueFacet,
    )

}
