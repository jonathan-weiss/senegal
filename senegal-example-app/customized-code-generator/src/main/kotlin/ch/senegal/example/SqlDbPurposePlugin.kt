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
        enclosingConceptName = EntitiesConceptPlugin.conceptName
    )

    val sqlDbResourceBasePathFacet = FacetFactory.DirectoryFacetFactory.createFacet(
        facetName = FacetName.of("ResourceBasePath"),
        enclosingConceptName = EntitiesConceptPlugin.conceptName
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
        ?.let { CaseUtil.camelToSnakeCaseAllCaps(it) }
    }

    val sqlDbPrimaryKeyColumnTypeFacet = FacetFactory.StringFacetFactory.createCalculatedFacet(
        facetName = FacetName.of("PrimaryKeyColumnType"),
        enclosingConceptName = EntityConceptPlugin.conceptName
    ) { _: ModelNode ->
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
    ) { _: ModelNode ->
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
    private val sqlIntType = StringEnumerationFacetOption("INTEGER")
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

        return when(modelNode.concept().conceptName) {
            // EntitiesConceptPlugin.conceptName -> createEntitiesTemplates(modelNode)
            EntityConceptPlugin.conceptName -> createEntityTemplates(modelNode)
            else -> emptySet()
        }
    }
    private fun createEntitiesTemplates(modelNode: ModelNode): Set<TemplateTarget> {
        val targets: MutableSet<TemplateTarget> = mutableSetOf()

        val targetBaseResourcesPath = modelNode.getDirectoryFacetValue(purposeName, sqlDbResourceBasePathFacet.facetName)

        if(targetBaseResourcesPath != null) {
            targets.add(TemplateTarget(targetBaseResourcesPath.resolve("db/changelog/structure.xml"), TemplateForFreemarker("/ch/senegal/pluginexample/sql-db-liquibase-includes-xml.ftl")))
        }
        return targets
    }

    private fun createEntityTemplates(modelNode: ModelNode): Set<TemplateTarget> {
        val targets: MutableSet<TemplateTarget> = mutableSetOf()

        val targetBasePath = modelNode.parentModelNode()?.getDirectoryFacetValue(purposeName, sqlDbTargetBasePathFacet.facetName)
        val targetBaseResourcesPath = modelNode.parentModelNode()?.getDirectoryFacetValue(purposeName, sqlDbResourceBasePathFacet.facetName)
        val kotlinModelClassName = modelNode.getStringFacetValue(KotlinModelPurposePlugin.purposeName, KotlinModelPurposePlugin.kotlinModelClassnameFacet.facetName)
        val jpaEntityName = modelNode.getStringFacetValue(purposeName, sqlDbJpaEntityNameFacet.facetName)
        val tableName = modelNode.getStringFacetValue(purposeName, sqlDbTableNameFacet.facetName)
        val jpaEntityPackage = modelNode.getStringFacetValue(purposeName, sqlDbJpaEntityPackageFacet.facetName)


        if(jpaEntityName != null && jpaEntityPackage != null && targetBasePath != null && targetBaseResourcesPath != null && tableName != null) {
            val directory = jpaEntityPackage.replace(".", "/")
            targets.add(TemplateTarget(targetBasePath.resolve("$directory/${jpaEntityName}.kt"), TemplateForFreemarker("/ch/senegal/pluginexample/sql-db-jpa-entity.ftl")))
            targets.add(TemplateTarget(targetBasePath.resolve("$directory/${jpaEntityName}Repository.kt"), TemplateForFreemarker("/ch/senegal/pluginexample/sql-db-jpa-repository.ftl")))
            targets.add(TemplateTarget(targetBasePath.resolve("$directory/${kotlinModelClassName}RepositoryImpl.kt"), TemplateForFreemarker("/ch/senegal/pluginexample/sql-db-repository-impl.ftl")))
            // targets.add(TemplateTarget(targetBaseResourcesPath.resolve("db/changelog/${tableName}.structure.xml"), TemplateForFreemarker("/ch/senegal/pluginexample/sql-db-liquibase-xml.ftl")))
        }

        return targets
    }

    override val facets: Set<Facet> = setOf(
        sqlDbTargetBasePathFacet,
        sqlDbResourceBasePathFacet,
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
