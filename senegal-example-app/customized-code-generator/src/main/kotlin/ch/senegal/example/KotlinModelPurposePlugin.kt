package ch.senegal.example

import ch.senegal.engine.util.CaseUtil
import ch.senegal.plugin.*
import ch.senegal.plugin.factory.FacetFactory
import ch.senegal.plugin.model.ModelNode
import java.nio.file.Path


object KotlinModelPurposePlugin : Purpose {
    override val purposeName: PurposeName = PurposeName.of("KotlinModel")

    val kotlinModelTargetBasePathFacet = FacetFactory.DirectoryFacetFactory.createFacet(
        facetName = FacetName.of("TargetBasePath"),
        enclosingConceptName = EntitiesConceptPlugin.conceptName
    )


    val kotlinModelClassnameFacet = FacetFactory.StringFacetFactory.createCalculatedFacet(
        facetName = FacetName.of("ClassName"),
        enclosingConceptName = EntityConceptPlugin.conceptName
    ) { modelNode: ModelNode -> modelNode
        .getStringFacetValue(EntityPurposePlugin.purposeName, EntityPurposePlugin.entityNameFacet.facetName)
    }

    val kotlinModelPackageFacet = FacetFactory.StringFacetFactory.createFacet(
        facetName = FacetName.of("Package"),
        enclosingConceptName = EntityConceptPlugin.conceptName
    ) { modelNode: ModelNode, basePackage: String? ->
        val entityName = modelNode.getStringFacetValue(EntityPurposePlugin.purposeName, EntityPurposePlugin.entityNameFacet.facetName)
        val className = entityName?.lowercase()

        if(basePackage == null || className == null) {
            return@createFacet null
        }
        return@createFacet "$basePackage.$className"
    }

    val kotlinModelIdFieldTypeFacet = FacetFactory.StringFacetFactory.createCalculatedFacet(
        facetName = FacetName.of("IdFieldType"),
        enclosingConceptName = EntityConceptPlugin.conceptName
    ) { modelNode: ModelNode ->
        val classname = modelNode.getStringFacetValue(EntityPurposePlugin.purposeName, EntityPurposePlugin.entityNameFacet.facetName)
            ?: return@createCalculatedFacet null

        return@createCalculatedFacet "${classname}Id"
    }

    val kotlinModelIdFieldNameFacet = FacetFactory.StringFacetFactory.createCalculatedFacet(
        facetName = FacetName.of("IdFieldName"),
        enclosingConceptName = EntityConceptPlugin.conceptName
    ) { modelNode: ModelNode ->
        val classname = modelNode.getStringFacetValue(EntityPurposePlugin.purposeName, EntityPurposePlugin.entityNameFacet.facetName)
            ?: return@createCalculatedFacet null

        return@createCalculatedFacet "${CaseUtil.decapitalize(classname)}Id"
    }


    val kotlinModelFieldNameFacet = FacetFactory.StringFacetFactory.createCalculatedFacet(
        facetName = FacetName.of("FieldName"),
        enclosingConceptName = EntityAttributeConceptPlugin.conceptName
    ) { modelNode: ModelNode ->
        modelNode.getStringFacetValue(EntityPurposePlugin.purposeName, EntityPurposePlugin.entityAttributeNameFacet.facetName)
    }

    val kotlinStringType = StringEnumerationFacetOption("kotlin.String")
    val kotlinIntType = StringEnumerationFacetOption("kotlin.Int")
    val kotlinBooleanType = StringEnumerationFacetOption("kotlin.Boolean")

    val kotlinModelFieldTypeFacet = FacetFactory.StringEnumerationFacetFactory.createCalculatedFacet(
        facetName = FacetName.of("FieldType"),
        enclosingConceptName = EntityAttributeConceptPlugin.conceptName,
        enumerationOptions = listOf(kotlinStringType, kotlinIntType, kotlinBooleanType)
    ) { modelNode: ModelNode ->


        val entityAttributeTypeOption =  modelNode.getEnumFacetOption(EntityPurposePlugin.purposeName, EntityPurposePlugin.entityAttributeTypeFacet.facetName)
            ?: return@createCalculatedFacet null
        return@createCalculatedFacet when(entityAttributeTypeOption) {
            EntityPurposePlugin.entityAttributeTextType -> kotlinStringType.name
            EntityPurposePlugin.entityAttributeIntegerNumberType -> kotlinIntType.name
            EntityPurposePlugin.entityAttributeBooleanType -> kotlinBooleanType.name
            else -> null
        }

    }

    override fun createTemplateTargets(modelNode: ModelNode, defaultOutputPath: Path): Set<TemplateTarget> {
        if(modelNode.concept().conceptName != EntityConceptPlugin.conceptName) {
            return emptySet()
        }

        return createEntityTemplates(modelNode)
    }

    private fun createEntityTemplates(modelNode: ModelNode): Set<TemplateTarget> {
        val targets: MutableSet<TemplateTarget> = mutableSetOf()

        val className = modelNode.getStringFacetValue(purposeName, kotlinModelClassnameFacet.facetName)
        val packageName = modelNode.getStringFacetValue(purposeName, kotlinModelPackageFacet.facetName)
        val targetBasePath = modelNode.parentModelNode()?.getDirectoryFacetValue(purposeName, kotlinModelTargetBasePathFacet.facetName)

        if(className != null && packageName != null && targetBasePath != null) {
            val directory = packageName.replace(".", "/")
            targets.add(TemplateTarget(targetBasePath.resolve("$directory/${className}.kt"), TemplateForFreemarker("/ch/senegal/pluginexample/kotlin-model-class.ftl")))
            targets.add(TemplateTarget(targetBasePath.resolve("$directory/${className}Id.kt"), TemplateForFreemarker("/ch/senegal/pluginexample/kotlin-model-id-class.ftl")))
            targets.add(TemplateTarget(targetBasePath.resolve("$directory/${className}Repository.kt"), TemplateForFreemarker("/ch/senegal/pluginexample/kotlin-model-repository.ftl")))
            targets.add(TemplateTarget(targetBasePath.resolve("$directory/Create${className}Instruction.kt"), TemplateForFreemarker("/ch/senegal/pluginexample/kotlin-model-create-instruction.ftl")))
            targets.add(TemplateTarget(targetBasePath.resolve("$directory/Update${className}Instruction.kt"), TemplateForFreemarker("/ch/senegal/pluginexample/kotlin-model-update-instruction.ftl")))
            targets.add(TemplateTarget(targetBasePath.resolve("$directory/Delete${className}Instruction.kt"), TemplateForFreemarker("/ch/senegal/pluginexample/kotlin-model-delete-instruction.ftl")))
            targets.add(TemplateTarget(targetBasePath.resolve("$directory/${className}Service.kt"), TemplateForFreemarker("/ch/senegal/pluginexample/kotlin-model-service.ftl")))
        }

        return targets

    }


    override val facets: Set<Facet> = setOf(
        kotlinModelTargetBasePathFacet,
        kotlinModelClassnameFacet,
        kotlinModelPackageFacet,
        kotlinModelFieldNameFacet,
        kotlinModelFieldTypeFacet,
        kotlinModelIdFieldTypeFacet,
        kotlinModelIdFieldNameFacet,
    )

}
