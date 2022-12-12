package ch.senegal.example

import ch.senegal.plugin.*
import ch.senegal.plugin.model.ModelNode
import java.nio.file.Path


object KotlinModelPurposePlugin : Purpose {
    override val purposeName: PurposeName = PurposeName.of("KotlinModel")
    override val facets: Set<Facet> = setOf(KotlinModelClassnameFacet, KotlinModelPackageFacet)
    override fun createTemplateTargets(modelNode: ModelNode, defaultOutputPath: Path): Set<TemplateTarget> {

        val targets: MutableSet<TemplateTarget> = mutableSetOf()
        targets.add(TemplateTarget(defaultOutputPath.resolve("general-template.txt"), "/ch/senegal/pluginexample/general-template.ftl"))

        val className = modelNode.getFacetValue(purposeName, KotlinModelClassnameFacet.facetName)?.value as String?
        val packageName = modelNode.getFacetValue(purposeName, KotlinModelPackageFacet.facetName)?.value as String?

        if(className != null && packageName != null) {
            val directory = packageName.replace(".", "/")
            targets.add(TemplateTarget(defaultOutputPath.resolve("$directory/$className.kt"), "/ch/senegal/pluginexample/kotlin-model-class.ftl"))
        }

        return targets
    }
}

object KotlinModelClassnameFacet : Facet {
    override val facetName: FacetName = FacetName.of("ClassName")
    override val enclosingConceptName = EntityConceptPlugin.conceptName
    override val facetType: FacetType = TextFacetType
}

object KotlinModelPackageFacet : Facet {
    override val facetName: FacetName = FacetName.of("Package")
    override val enclosingConceptName = EntityConceptPlugin.conceptName
    override val facetType: FacetType = TextFacetType
}

object KotlinFieldPurposePlugin : Purpose {
    override val purposeName: PurposeName = PurposeName.of("KotlinField")
    override val facets: Set<Facet> = setOf(KotlinFieldNameFacet, KotlinFieldTypeFacet)
}

object KotlinFieldNameFacet : Facet {
    override val facetName: FacetName = FacetName.of("Name")
    override val enclosingConceptName = EntityAttributeConceptPlugin.conceptName
    override val facetType: FacetType = TextFacetType
}

object KotlinFieldTypeFacet : Facet {
    override val facetName: FacetName = FacetName.of("Type")
    override val enclosingConceptName = EntityAttributeConceptPlugin.conceptName
    override val facetType: FacetType = EnumerationFacetType(
        listOf(
            FacetTypeEnumerationValue("kotlin.String"),
            FacetTypeEnumerationValue("kotlin.Int"),
            FacetTypeEnumerationValue("kotlin.Boolean"),
        )
    )
}
