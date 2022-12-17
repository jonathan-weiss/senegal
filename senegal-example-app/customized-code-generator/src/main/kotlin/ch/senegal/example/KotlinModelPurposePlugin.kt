package ch.senegal.example

import ch.senegal.plugin.*
import ch.senegal.plugin.model.FacetValue
import ch.senegal.plugin.model.ModelNode
import java.nio.file.Path


object KotlinModelPurposePlugin : Purpose {
    override val purposeName: PurposeName = PurposeName.of("KotlinModel")
    override val facets: Set<Facet> = setOf(
        KotlinModelClassnameFacet,
        KotlinModelPackageFacet,
        KotlinModelTargetBasePathFacet,
        KotlinModelFieldNameFacet,
        KotlinModelFieldTypeFacet,
    )
    override fun createTemplateTargets(modelNode: ModelNode, defaultOutputPath: Path): Set<TemplateTarget> {

        val targets: MutableSet<TemplateTarget> = mutableSetOf()
        targets.add(TemplateTarget(defaultOutputPath.resolve("template-tree.txt"), "/ch/senegal/pluginexample/general-template.ftl"))

        val className = modelNode.getFacetValue(purposeName, KotlinModelClassnameFacet.facetName)?.value as String?
        val packageName = modelNode.getFacetValue(purposeName, KotlinModelPackageFacet.facetName)?.value as String?
        val targetBasePath = modelNode.getFacetValue(purposeName, KotlinModelTargetBasePathFacet.facetName)?.value as Path?

        if(className != null && packageName != null && targetBasePath != null) {
            val directory = packageName.replace(".", "/")
            targets.add(TemplateTarget(targetBasePath.resolve("$directory/$className.kt"), "/ch/senegal/pluginexample/kotlin-model-class.ftl"))
        }

        return targets
    }
}
object KotlinModelTargetBasePathFacet : Facet {
    override val facetName: FacetName = FacetName.of("TargetBasePath")
    override val enclosingConceptName = EntityConceptPlugin.conceptName
    override val facetType: FacetType = DirectoryFacetType
}


object KotlinModelClassnameFacet : Facet {
    override val facetName: FacetName = FacetName.of("ClassName")
    override val enclosingConceptName = EntityConceptPlugin.conceptName
    override val facetType: FacetType = TextFacetType
    override val isOnlyCalculated: Boolean = true

    override fun calculateFacetValue(modelNode: ModelNode, facetValue: FacetValue?): FacetValue? {
        return modelNode.getFacetValue(EntityPurposePlugin.purposeName, EntityNameFacet.facetName)
    }
}

object KotlinModelPackageFacet : Facet {
    override val facetName: FacetName = FacetName.of("Package")
    override val enclosingConceptName = EntityConceptPlugin.conceptName
    override val facetType: FacetType = TextFacetType
}

object KotlinModelFieldNameFacet : Facet {
    override val facetName: FacetName = FacetName.of("FieldName")
    override val enclosingConceptName = EntityAttributeConceptPlugin.conceptName
    override val facetType: FacetType = TextFacetType
    override val isOnlyCalculated: Boolean = true

    override fun calculateFacetValue(modelNode: ModelNode, facetValue: FacetValue?): FacetValue? {
        return modelNode.getFacetValue(EntityPurposePlugin.purposeName, EntityAttributeNameFacet.facetName)
    }

}

object KotlinModelFieldTypeFacet : Facet {
    private val stringType = FacetTypeEnumerationValue("kotlin.String")
    private val intType = FacetTypeEnumerationValue("kotlin.Int")
    private val booleanType = FacetTypeEnumerationValue("kotlin.Boolean")

    override val facetName: FacetName = FacetName.of("FieldType")
    override val enclosingConceptName = EntityAttributeConceptPlugin.conceptName
    override val facetType: FacetType = EnumerationFacetType(
        listOf(stringType, intType, booleanType)
    )
    override val isOnlyCalculated: Boolean = true
    override fun calculateFacetValue(modelNode: ModelNode, facetValue: FacetValue?): FacetValue? {
        val entityFacetValue =  modelNode.getFacetValue(EntityPurposePlugin.purposeName, EntityAttributeTypeFacet.facetName)
        return when(entityFacetValue?.value) {
            EntityAttributeTypeFacet.textType.name -> FacetValue.of(stringType.name)
            EntityAttributeTypeFacet.integerNumberType.name -> FacetValue.of(intType.name)
            EntityAttributeTypeFacet.booleanType.name -> FacetValue.of(booleanType.name)
            else -> null
        }
    }

}
