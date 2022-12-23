package ch.senegal.pluginexample

import ch.senegal.plugin.*
import ch.senegal.plugin.factory.FacetFactory
import ch.senegal.plugin.model.ModelNode
import ch.senegal.pluginexample.EntityAttributePurposePlugin.typeFacetName
import java.nio.file.Path


object KotlinModelPurposePlugin : Purpose {
    override val purposeName: PurposeName = PurposeName.of("KotlinModel")
    override val facets: Set<Facet> = setOf(kotlinModelClassnameFacet, kotlinModelPackageFacet)
    override fun createTemplateTargets(modelNode: ModelNode, defaultOutputPath: Path): Set<TemplateTarget> {

        val targets: MutableSet<TemplateTarget> = mutableSetOf()
        targets.add(TemplateTarget(defaultOutputPath.resolve("general-template.txt"), TemplateForFreemarker("/ch/senegal/pluginexample/general-template.ftl")))

        val className = modelNode.getStringFacetValue(purposeName, kotlinModelClassnameFacet.facetName)
        val packageName = modelNode.getStringFacetValue(purposeName, kotlinModelPackageFacet.facetName)

        if(className != null && packageName != null) {
            val directory = packageName.replace(".", "/")
            targets.add(TemplateTarget(defaultOutputPath.resolve("$directory/$className.kt"), TemplateForFreemarker("/ch/senegal/pluginexample/kotlin-model-class.ftl")))
        }

        return targets
    }
}

val kotlinModelClassnameFacet = FacetFactory.StringFacetFactory.createFacet(
    facetName = FacetName.of("ClassName"),
    enclosingConceptName = EntityConceptPlugin.conceptName,
)
val kotlinModelPackageFacet = FacetFactory.StringFacetFactory.createFacet(
    facetName = FacetName.of("Package"),
    enclosingConceptName = EntityConceptPlugin.conceptName,
)



object KotlinFieldPurposePlugin : Purpose {
    override val purposeName: PurposeName = PurposeName.of("KotlinField")
    override val facets: Set<Facet> = setOf(kotlinFieldNameFacet,kotlinFieldTypeFacet)
}

val kotlinFieldNameFacet = FacetFactory.StringFacetFactory.createFacet(
    facetName = FacetName.of("Name"),
    enclosingConceptName = EntityAttributeConceptPlugin.conceptName,
)

val stringKotlinTypeOption = StringEnumerationFacetOption("kotlin.String")
val intKotlinTypeOption = StringEnumerationFacetOption("kotlin.Int")
val booleanKotlinTypeOption = StringEnumerationFacetOption("kotlin.Boolean")


val kotlinFieldTypeFacet = FacetFactory.StringEnumerationFacetFactory.createCalculatedFacet(
    facetName = FacetName.of("Type"),
    enclosingConceptName = EntityAttributeConceptPlugin.conceptName,
    enumerationOptions = listOf(stringKotlinTypeOption, intKotlinTypeOption, booleanKotlinTypeOption),
) { modelNode ->
    when(modelNode.getEnumFacetOption(EntityAttributePurposePlugin.purposeName, typeFacetName)) {
        EntityAttributePurposePlugin.textFieldOption -> stringKotlinTypeOption.name
        EntityAttributePurposePlugin.booleanFieldOption -> booleanKotlinTypeOption.name
        EntityAttributePurposePlugin.numberFieldOption -> intKotlinTypeOption.name
        else -> null
    }
}
