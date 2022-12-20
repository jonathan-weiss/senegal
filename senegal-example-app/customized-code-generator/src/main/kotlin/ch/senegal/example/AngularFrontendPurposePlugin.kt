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

    val angularFrontendDecapitalizedEntityName = FacetFactory.StringFacetFactory.createCalculatedFacet(
        facetName = FacetName.of("DecapitalizedEntityName"),
        enclosingConceptName = EntityConceptPlugin.conceptName
    ) { modelNode: ModelNode -> modelNode
        .getStringFacetValue(EntityPurposePlugin.purposeName, EntityPurposePlugin.entityNameFacet.facetName)
        ?.let { CaseUtil.decapitalize(it) }
    }

    val angularFrontendEntityName = FacetFactory.StringFacetFactory.createCalculatedFacet(
        facetName = FacetName.of("EntityName"),
        enclosingConceptName = EntityConceptPlugin.conceptName
    ) { modelNode: ModelNode -> modelNode
        .getStringFacetValue(EntityPurposePlugin.purposeName, EntityPurposePlugin.entityNameFacet.facetName)
    }

    val angularFrontendEntityFileName = FacetFactory.StringFacetFactory.createCalculatedFacet(
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
        return emptySet()
    }

    private fun createEntityTemplates(modelNode: ModelNode): Set<TemplateTarget> {
        val targets: MutableSet<TemplateTarget> = mutableSetOf()

        val angularFrontendBasePath = modelNode.parentModelNode()?.getDirectoryFacetValue(purposeName, angularFrontendBasePathFacet.facetName)
        val transferObjectFilename = modelNode.getStringFacetValue(purposeName, angularFrontendTransferObjectFilenameFacet.facetName)
        val transferObjectClassName = modelNode.getStringFacetValue(purposeName, angularFrontendTransferObjectNameFacet.facetName)
        val serviceName = modelNode.getStringFacetValue(purposeName, angularFrontendServiceNameFacet.facetName)
        val serviceFilename = modelNode.getStringFacetValue(purposeName, angularFrontendServiceFilenameFacet.facetName)


        if(angularFrontendBasePath != null && transferObjectFilename != null) {
            targets.add(TemplateTarget(angularFrontendBasePath.resolve("model/${transferObjectFilename}.ts"), "/ch/senegal/pluginexample/angular-frontend-transfer-object.ftl"))
        }

        if(angularFrontendBasePath != null && serviceName != null && serviceFilename != null) {
            targets.add(TemplateTarget(angularFrontendBasePath.resolve("api/${serviceFilename}.ts"), "/ch/senegal/pluginexample/angular-frontend-service.ftl"))
        }

        return targets
    }

    override val facets: Set<Facet> = setOf(
        angularFrontendBasePathFacet,
        angularFrontendDecapitalizedEntityName,
        angularFrontendEntityName,
        angularFrontendEntityFileName,
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
