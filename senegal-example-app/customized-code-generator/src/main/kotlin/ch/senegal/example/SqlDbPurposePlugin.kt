package ch.senegal.example

import ch.senegal.engine.util.CaseUtil
import ch.senegal.plugin.*
import ch.senegal.plugin.factory.FacetFactory
import ch.senegal.plugin.model.ModelNode
import java.nio.file.Path


object SqlDbPurposePlugin : Purpose {
    override val purposeName: PurposeName = PurposeName.of("SqlDb")

    val sqlDbTargetBasePathFacet = FacetFactory.DirectoryFacetFactory.createFacet(
        facetName = FacetName.of("TargetBasePath"),
        enclosingConceptName = EntityConceptPlugin.conceptName
    )

    val sqlDbJpaEntityNameFacet = FacetFactory.StringFacetFactory.createCalculatedFacet(
        facetName = FacetName.of("JpaEntityName"),
        enclosingConceptName = EntityConceptPlugin.conceptName
    ) { modelNode: ModelNode -> modelNode
        .getStringFacetValue(EntityPurposePlugin.purposeName, EntityPurposePlugin.entityNameFacet.facetName)
        ?.let { "${it}JpaEntity" }
    }

    val sqlDbJpaEntityPackageFacet = FacetFactory.StringFacetFactory.createFacet(
        facetName = FacetName.of("JpaEntityPackage"),
        enclosingConceptName = EntityConceptPlugin.conceptName
    ) { modelNode: ModelNode, basePackage: String? ->
        val entityName = modelNode.getStringFacetValue(EntityPurposePlugin.purposeName, EntityPurposePlugin.entityNameFacet.facetName)
        val lowercaseEntityName = entityName?.lowercase()

        if(basePackage == null || lowercaseEntityName == null) {
            return@createFacet null
        }
        return@createFacet "$basePackage.$lowercaseEntityName"
    }

    val sqlDbTableNameFacet = FacetFactory.StringFacetFactory.createCalculatedFacet(
        facetName = FacetName.of("TableName"),
        enclosingConceptName = EntityConceptPlugin.conceptName
    ) { modelNode: ModelNode -> modelNode
        .getStringFacetValue(EntityPurposePlugin.purposeName, EntityPurposePlugin.entityNameFacet.facetName)
        ?.let { CaseUtil.snakeCaseToSnakeCaseAllCaps(it) }
    }

    val sqlDbPrimaryKeyColumnTypeFacet = FacetFactory.StringFacetFactory.createCalculatedFacet(
        facetName = FacetName.of("PrimaryKeyColumnType"),
        enclosingConceptName = EntityConceptPlugin.conceptName
    ) { modelNode: ModelNode ->
        return@createCalculatedFacet "UUID"
    }

    val sqlDbPrimaryKeyColumnNameFacet = FacetFactory.StringFacetFactory.createCalculatedFacet(
        facetName = FacetName.of("PrimaryKeyColumnName"),
        enclosingConceptName = EntityConceptPlugin.conceptName
    ) { modelNode: ModelNode ->
        val entityName = modelNode.getStringFacetValue(EntityPurposePlugin.purposeName, EntityPurposePlugin.entityNameFacet.facetName)
            ?: return@createCalculatedFacet null

        return@createCalculatedFacet CaseUtil.camelToSnakeCaseAllCaps("${entityName}Id")
    }

    val sqlDbPrimaryKeyJpaFieldTypeFacet = FacetFactory.StringFacetFactory.createCalculatedFacet(
        facetName = FacetName.of("PrimaryKeyJpaFieldType"),
        enclosingConceptName = EntityConceptPlugin.conceptName
    ) { modelNode: ModelNode ->
        return@createCalculatedFacet "UUID"
    }

    val sqlDbPrimaryKeyJpaFieldNameFacet = FacetFactory.StringFacetFactory.createCalculatedFacet(
        facetName = FacetName.of("PrimaryKeyJpaFieldName"),
        enclosingConceptName = EntityConceptPlugin.conceptName
    ) { modelNode: ModelNode ->
        val entityName = modelNode.getStringFacetValue(EntityPurposePlugin.purposeName, EntityPurposePlugin.entityNameFacet.facetName)
            ?: return@createCalculatedFacet null

        return@createCalculatedFacet CaseUtil.decapitalize("${entityName}Id")
    }

    val sqlDbColumnNameFacet = FacetFactory.StringFacetFactory.createCalculatedFacet(
        facetName = FacetName.of("ColumnName"),
        enclosingConceptName = EntityAttributeConceptPlugin.conceptName
    ) { modelNode: ModelNode -> modelNode
        .getStringFacetValue(EntityPurposePlugin.purposeName, EntityPurposePlugin.entityAttributeNameFacet.facetName)
        ?.let { CaseUtil.camelToSnakeCaseAllCaps(it) }
    }

    val sqlDbJpaFieldNameFacet = FacetFactory.StringFacetFactory.createCalculatedFacet(
        facetName = FacetName.of("JpaFieldName"),
        enclosingConceptName = EntityAttributeConceptPlugin.conceptName
    ) { modelNode: ModelNode -> modelNode
        .getStringFacetValue(EntityPurposePlugin.purposeName, EntityPurposePlugin.entityAttributeNameFacet.facetName)
    }


    private val sqlStringType = StringEnumerationFacetOption("VARCHAR(255)")
    private val sqlIntType = StringEnumerationFacetOption("NUMBER")
    private val sqlBooleanType = StringEnumerationFacetOption("BOOLEAN")

    val sqlDbColumnTypeFacet = FacetFactory.StringEnumerationFacetFactory.createCalculatedFacet(
        facetName = FacetName.of("ColumnType"),
        enclosingConceptName = EntityAttributeConceptPlugin.conceptName,
        enumerationOptions = listOf(sqlStringType, sqlIntType, sqlBooleanType)
    ) { modelNode: ModelNode ->


        val entityAttributeTypeOption =  modelNode.getEnumFacetOption(EntityPurposePlugin.purposeName, EntityPurposePlugin.entityAttributeTypeFacet.facetName)
            ?: return@createCalculatedFacet null
        return@createCalculatedFacet when(entityAttributeTypeOption) {
            EntityPurposePlugin.entityAttributeTextType -> sqlStringType.name
            EntityPurposePlugin.entityAttributeIntegerNumberType -> sqlIntType.name
            EntityPurposePlugin.entityAttributeBooleanType -> sqlBooleanType.name
            else -> null
        }

    }

    val sqlDbJpaFieldTypeFacet = FacetFactory.StringEnumerationFacetFactory.createCalculatedFacet(
        facetName = FacetName.of("JpaFieldType"),
        enclosingConceptName = EntityAttributeConceptPlugin.conceptName,
        enumerationOptions = listOf(
            KotlinModelPurposePlugin.kotlinStringType,
            KotlinModelPurposePlugin.kotlinIntType,
            KotlinModelPurposePlugin.kotlinBooleanType
        )
    ) { modelNode: ModelNode ->


        val entityAttributeTypeOption =  modelNode.getEnumFacetOption(EntityPurposePlugin.purposeName, EntityPurposePlugin.entityAttributeTypeFacet.facetName)
            ?: return@createCalculatedFacet null
        return@createCalculatedFacet when(entityAttributeTypeOption) {
            EntityPurposePlugin.entityAttributeTextType -> KotlinModelPurposePlugin.kotlinStringType.name
            EntityPurposePlugin.entityAttributeIntegerNumberType -> KotlinModelPurposePlugin.kotlinIntType.name
            EntityPurposePlugin.entityAttributeBooleanType -> KotlinModelPurposePlugin.kotlinBooleanType.name
            else -> null
        }

    }

    override fun createTemplateTargets(modelNode: ModelNode, defaultOutputPath: Path): Set<TemplateTarget> {
        if(modelNode.concept().conceptName != EntityConceptPlugin.conceptName) {
            return emptySet()
        }

        val targets: MutableSet<TemplateTarget> = mutableSetOf()
        val jpaEntityName = modelNode.getStringFacetValue(purposeName, sqlDbJpaEntityNameFacet.facetName)
        val targetBasePath = modelNode.getDirectoryFacetValue(purposeName, sqlDbTargetBasePathFacet.facetName)
        val jpaEntityPackage = modelNode.getStringFacetValue(purposeName, sqlDbJpaEntityPackageFacet.facetName)


        if(jpaEntityName != null && jpaEntityPackage != null && targetBasePath != null) {
            val directory = jpaEntityPackage.replace(".", "/")
            targets.add(TemplateTarget(targetBasePath.resolve("$directory/${jpaEntityName}.kt"), "/ch/senegal/pluginexample/sql-db-jpa-entity.ftl"))
        }

        return targets
    }

    override val facets: Set<Facet> = setOf(
        sqlDbTargetBasePathFacet,
        sqlDbTableNameFacet,
        sqlDbJpaEntityNameFacet,
        sqlDbJpaEntityPackageFacet,
        sqlDbPrimaryKeyColumnTypeFacet,
        sqlDbPrimaryKeyColumnNameFacet,
        sqlDbPrimaryKeyJpaFieldNameFacet,
        sqlDbPrimaryKeyJpaFieldTypeFacet,
        sqlDbColumnNameFacet,
        sqlDbColumnTypeFacet,
        sqlDbJpaFieldNameFacet,
        sqlDbJpaFieldTypeFacet,
    )

}
