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

    val angularFrontendServiceNameFacet = FacetFactory.StringFacetFactory.createCalculatedFacet(
        facetName = FacetName.of("ServiceName"),
        enclosingConceptName = EntityConceptPlugin.conceptName
    ) { modelNode: ModelNode -> modelNode
        .getStringFacetValue(EntityPurposePlugin.purposeName, EntityPurposePlugin.entityNameFacet.facetName)
        ?.let { "${it}ApiService" }
    }

    val angularFrontendServiceFilenameFacet = FacetFactory.StringFacetFactory.createCalculatedFacet(
        facetName = FacetName.of("ServiceFilename"),
        enclosingConceptName = EntityConceptPlugin.conceptName
    ) { modelNode: ModelNode -> modelNode
        .getStringFacetValue(EntityPurposePlugin.purposeName, EntityPurposePlugin.entityNameFacet.facetName)
        ?.let { CaseUtil.camelToDashCase(it) + "-api.service" }
    }

    val angularFrontendTransferObjectNameFacet = FacetFactory.StringFacetFactory.createCalculatedFacet(
        facetName = FacetName.of("TransferObjectName"),
        enclosingConceptName = EntityConceptPlugin.conceptName
    ) { modelNode: ModelNode -> modelNode
        .getStringFacetValue(EntityPurposePlugin.purposeName, EntityPurposePlugin.entityNameFacet.facetName)
        ?.let { "${it}TO" }
    }

    val angularFrontendTransferObjectIdFieldTypeFacet = FacetFactory.StringFacetFactory.createCalculatedFacet(
        facetName = FacetName.of("TransferObjectIdFieldType"),
        enclosingConceptName = EntityConceptPlugin.conceptName
    ) { modelNode: ModelNode ->
        return@createCalculatedFacet "UuidTO"
    }

    val angularFrontendTransferObjectFilenameFacet = FacetFactory.StringFacetFactory.createCalculatedFacet(
        facetName = FacetName.of("TransferObjectFilename"),
        enclosingConceptName = EntityConceptPlugin.conceptName
    ) { modelNode: ModelNode -> modelNode
        .getStringFacetValue(EntityPurposePlugin.purposeName, EntityPurposePlugin.entityNameFacet.facetName)
        ?.let { CaseUtil.camelToDashCase(it) + "-to.model" }
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
        facetName = FacetName.of("EntityFileName"),
        enclosingConceptName = EntityConceptPlugin.conceptName
    ) { modelNode: ModelNode -> modelNode
        .getStringFacetValue(EntityPurposePlugin.purposeName, EntityPurposePlugin.entityNameFacet.facetName)
        ?.let { CaseUtil.camelToDashCase(it) }
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
            targets.add(TemplateTarget(angularFrontendBasePath.resolve("generated-entities.module.ts"), "/ch/senegal/pluginexample/angular-frontent-component-generated-entities-module-ts.ftl"))
            targets.add(TemplateTarget(angularFrontendBasePath.resolve("generated-entities-routing.module.ts"), "/ch/senegal/pluginexample/angular-frontent-component-generated-entities-routing-module-ts.ftl"))
        }

        return targets

    }

    private fun createEntityTemplates(modelNode: ModelNode): Set<TemplateTarget> {
        val targets: MutableSet<TemplateTarget> = mutableSetOf()

        val angularFrontendBasePath = modelNode.parentModelNode()?.getDirectoryFacetValue(purposeName, angularFrontendBasePathFacet.facetName)
        val transferObjectFilename = modelNode.getStringFacetValue(purposeName, angularFrontendTransferObjectFilenameFacet.facetName)
        val entityFileName = modelNode.getStringFacetValue(purposeName, angularFrontendEntityFileNameFacet.facetName)
        val serviceName = modelNode.getStringFacetValue(purposeName, angularFrontendServiceNameFacet.facetName)
        val serviceFilename = modelNode.getStringFacetValue(purposeName, angularFrontendServiceFilenameFacet.facetName)


        if(angularFrontendBasePath != null && transferObjectFilename != null) {
            targets.add(TemplateTarget(angularFrontendBasePath.resolve("${entityFileName}/api/${transferObjectFilename}.ts"), "/ch/senegal/pluginexample/angular-frontend-transfer-object.ftl"))
        }

        if(angularFrontendBasePath != null && serviceName != null && serviceFilename != null) {
            targets.add(TemplateTarget(angularFrontendBasePath.resolve("${entityFileName}/api/${serviceFilename}.ts"), "/ch/senegal/pluginexample/angular-frontend-service.ftl"))
        }


        if(angularFrontendBasePath != null && entityFileName != null) {
            targets.add(TemplateTarget(angularFrontendBasePath.resolve("${entityFileName}/component/${entityFileName}-panel-view/${entityFileName}-panel-view.component.ts"), "/ch/senegal/pluginexample/angular-frontent-component-panel-view-ts.ftl"))
            targets.add(TemplateTarget(angularFrontendBasePath.resolve("${entityFileName}/component/${entityFileName}-panel-view/${entityFileName}-panel-view.component.scss"), "/ch/senegal/pluginexample/angular-frontent-component-panel-view-scss.ftl"))
            targets.add(TemplateTarget(angularFrontendBasePath.resolve("${entityFileName}/component/${entityFileName}-panel-view/${entityFileName}-panel-view.component.html"), "/ch/senegal/pluginexample/angular-frontent-component-panel-view-html.ftl"))

            targets.add(TemplateTarget(angularFrontendBasePath.resolve("${entityFileName}/component/${entityFileName}-table-view/${entityFileName}-table-view.component.ts"), "/ch/senegal/pluginexample/angular-frontent-component-table-view-ts.ftl"))
            targets.add(TemplateTarget(angularFrontendBasePath.resolve("${entityFileName}/component/${entityFileName}-table-view/${entityFileName}-table-view.component.scss"), "/ch/senegal/pluginexample/angular-frontent-component-table-view-scss.ftl"))
            targets.add(TemplateTarget(angularFrontendBasePath.resolve("${entityFileName}/component/${entityFileName}-table-view/${entityFileName}-table-view.component.html"), "/ch/senegal/pluginexample/angular-frontent-component-table-view-html.ftl"))

        }


        return targets
    }

    override val facets: Set<Facet> = setOf(
        angularFrontendBasePathFacet,
        angularFrontendDecapitalizedEntityNameFacet,
        angularFrontendEntityNameFacet,
        angularFrontendEntityFileNameFacet,
        angularFrontendTransferObjectNameFacet,
        angularFrontendTransferObjectFilenameFacet,
        angularFrontendServiceNameFacet,
        angularFrontendServiceFilenameFacet,
        angularFrontendTransferObjectFieldNameFacet,
        angularFrontendTransferObjectFieldTypeFacet,
        angularFrontendTransferObjectIdFieldNameFacet,
        angularFrontendTransferObjectIdFieldTypeFacet,
    )

}