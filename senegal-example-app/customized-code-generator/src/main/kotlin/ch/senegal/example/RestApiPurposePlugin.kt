package ch.senegal.example

import ch.senegal.engine.util.CaseUtil
import ch.senegal.plugin.*
import ch.senegal.plugin.factory.FacetFactory
import ch.senegal.plugin.model.ModelNode
import java.nio.file.Path


object RestApiPurposePlugin : Purpose {
    override val purposeName: PurposeName = PurposeName.of("RestApi")

    val restApiFacadeBasePathFacet = FacetFactory.DirectoryFacetFactory.createFacet(
        facetName = FacetName.of("FacadeBasePath"),
        enclosingConceptName = EntitiesConceptPlugin.conceptName
    )

    val restApiControllerBasePathFacet = FacetFactory.DirectoryFacetFactory.createFacet(
        facetName = FacetName.of("ControllerBasePath"),
        enclosingConceptName = EntitiesConceptPlugin.conceptName
    )

    val restApiFacadeNameFacet = FacetFactory.StringFacetFactory.createCalculatedFacet(
        facetName = FacetName.of("FacadeName"),
        enclosingConceptName = EntityConceptPlugin.conceptName
    ) { modelNode: ModelNode -> modelNode
        .getStringFacetValue(EntityPurposePlugin.purposeName, EntityPurposePlugin.entityNameFacet.facetName)
        ?.let { "${it}Facade" }
    }

    val restApiFacadePackageFacet = FacetFactory.StringFacetFactory.createFacet(
        facetName = FacetName.of("FacadePackage"),
        enclosingConceptName = EntityConceptPlugin.conceptName
    ) { modelNode: ModelNode, basePackage: String? ->
        val entityName = modelNode.getStringFacetValue(EntityPurposePlugin.purposeName, EntityPurposePlugin.entityNameFacet.facetName)
        val lowercaseEntityName = entityName?.lowercase()

        if(basePackage == null || lowercaseEntityName == null) {
            return@createFacet null
        }
        return@createFacet "$basePackage.$lowercaseEntityName"
    }

    val restApiControllerPackageFacet = FacetFactory.StringFacetFactory.createFacet(
        facetName = FacetName.of("ControllerPackage"),
        enclosingConceptName = EntityConceptPlugin.conceptName
    ) { modelNode: ModelNode, basePackage: String? ->
        val entityName = modelNode.getStringFacetValue(EntityPurposePlugin.purposeName, EntityPurposePlugin.entityNameFacet.facetName)
        val lowercaseEntityName = entityName?.lowercase()

        if(basePackage == null || lowercaseEntityName == null) {
            return@createFacet null
        }
        return@createFacet "$basePackage.$lowercaseEntityName"
    }
    val restApiUrlPrefixNameFacet = FacetFactory.StringFacetFactory.createCalculatedFacet(
        facetName = FacetName.of("UrlPrefixName"),
        enclosingConceptName = EntityConceptPlugin.conceptName
    ) { modelNode: ModelNode -> modelNode
        .getStringFacetValue(EntityPurposePlugin.purposeName, EntityPurposePlugin.entityNameFacet.facetName)
        ?.let { CaseUtil.camelToDashCase(it) }
    }


    val restApiTransferObjectNameFacet = FacetFactory.StringFacetFactory.createCalculatedFacet(
        facetName = FacetName.of("TransferObjectName"),
        enclosingConceptName = EntityConceptPlugin.conceptName
    ) { modelNode: ModelNode -> modelNode
        .getStringFacetValue(EntityPurposePlugin.purposeName, EntityPurposePlugin.entityNameFacet.facetName)
        ?.let { "${it}TO" }
    }

    val restApiIdFieldTypeFacet = FacetFactory.StringFacetFactory.createCalculatedFacet(
        facetName = FacetName.of("IdFieldType"),
        enclosingConceptName = EntityConceptPlugin.conceptName
    ) { modelNode: ModelNode ->
        return@createCalculatedFacet ""
    }

    val restApiIdFieldNameFacet = FacetFactory.StringFacetFactory.createCalculatedFacet(
        facetName = FacetName.of("IdFieldName"),
        enclosingConceptName = EntityConceptPlugin.conceptName
    ) { modelNode: ModelNode ->
        val entityName = modelNode.getStringFacetValue(EntityPurposePlugin.purposeName, EntityPurposePlugin.entityNameFacet.facetName)
            ?: return@createCalculatedFacet null

        return@createCalculatedFacet "${entityName}Id"
    }

    val restApiTransferObjectIdFieldTypeFacet = FacetFactory.StringFacetFactory.createCalculatedFacet(
        facetName = FacetName.of("TransferObjectIdFieldType"),
        enclosingConceptName = EntityConceptPlugin.conceptName
    ) { modelNode: ModelNode ->
        return@createCalculatedFacet "UuidTO"
    }

    val restApiTransferObjectIdFieldNameFacet = FacetFactory.StringFacetFactory.createCalculatedFacet(
        facetName = FacetName.of("TransferObjectIdFieldName"),
        enclosingConceptName = EntityConceptPlugin.conceptName
    ) { modelNode: ModelNode ->
        val entityName = modelNode.getStringFacetValue(EntityPurposePlugin.purposeName, EntityPurposePlugin.entityNameFacet.facetName)
            ?: return@createCalculatedFacet null

        return@createCalculatedFacet CaseUtil.decapitalize("${entityName}Id")
    }

    val restApiTransferObjectFieldNameFacet = FacetFactory.StringFacetFactory.createCalculatedFacet(
        facetName = FacetName.of("TransferObjectFieldName"),
        enclosingConceptName = EntityAttributeConceptPlugin.conceptName
    ) { modelNode: ModelNode -> modelNode
        .getStringFacetValue(EntityPurposePlugin.purposeName, EntityPurposePlugin.entityAttributeNameFacet.facetName)
    }


    private val transferObjectStringType = StringEnumerationFacetOption("kotlin.String")
    private val transferObjectIntType = StringEnumerationFacetOption("kotlin.Int")
    private val transferObjectBooleanType = StringEnumerationFacetOption("kotlin.Boolean")

    val restApiTransferObjectFieldTypeFacet = FacetFactory.StringEnumerationFacetFactory.createCalculatedFacet(
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

        val facadeBasePath = modelNode.parentModelNode()?.getDirectoryFacetValue(purposeName, restApiFacadeBasePathFacet.facetName)
        val controllerBasePath = modelNode.parentModelNode()?.getDirectoryFacetValue(purposeName, restApiControllerBasePathFacet.facetName)
        val transferObjectClassName = modelNode.getStringFacetValue(purposeName, restApiTransferObjectNameFacet.facetName)
        val facadePackageName = modelNode.getStringFacetValue(purposeName, restApiFacadePackageFacet.facetName)
        val controllerPackageName = modelNode.getStringFacetValue(purposeName, restApiControllerPackageFacet.facetName)
        val kotlinModelClassName = modelNode.getStringFacetValue(KotlinModelPurposePlugin.purposeName, KotlinModelPurposePlugin.kotlinModelClassnameFacet.facetName)


        if(facadeBasePath != null && controllerBasePath != null && facadePackageName != null && controllerPackageName != null && transferObjectClassName != null) {
            val facadeDirectory = facadePackageName.replace(".", "/")
            val controllerDirectory = controllerPackageName.replace(".", "/")

            targets.add(TemplateTarget(facadeBasePath.resolve("$facadeDirectory/${kotlinModelClassName}Facade.kt"), "/ch/senegal/pluginexample/rest-api-facade.ftl"))
            targets.add(TemplateTarget(facadeBasePath.resolve("$facadeDirectory/${transferObjectClassName}.kt"), "/ch/senegal/pluginexample/rest-api-transfer-object.ftl"))
            targets.add(TemplateTarget(controllerBasePath.resolve("$controllerDirectory/${kotlinModelClassName}Controller.kt"), "/ch/senegal/pluginexample/rest-api-controller.ftl"))
        }

        return targets
    }

    override val facets: Set<Facet> = setOf(
        restApiControllerBasePathFacet,
        restApiFacadeBasePathFacet,
        restApiControllerPackageFacet,
        restApiFacadeNameFacet,
        restApiFacadePackageFacet,
        restApiTransferObjectNameFacet,
        restApiUrlPrefixNameFacet,
        restApiIdFieldNameFacet,
        restApiIdFieldTypeFacet,
        restApiTransferObjectFieldNameFacet,
        restApiTransferObjectFieldTypeFacet,
        restApiTransferObjectIdFieldNameFacet,
        restApiTransferObjectIdFieldTypeFacet,
    )

}
