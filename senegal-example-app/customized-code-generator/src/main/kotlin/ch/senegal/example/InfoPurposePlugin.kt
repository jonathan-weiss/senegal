package ch.senegal.example

import ch.senegal.plugin.*
import ch.senegal.plugin.factory.FacetFactory
import ch.senegal.plugin.model.ModelNode
import java.nio.file.Path


object InfoPurposePlugin : Purpose {
    override val purposeName: PurposeName = PurposeName.of("Info")

    override fun createTemplateTargets(modelNode: ModelNode, defaultOutputPath: Path): Set<TemplateTarget> {
        if (modelNode.concept().conceptName != EntitiesConceptPlugin.conceptName) {
            return emptySet()
        }

        val generalTemplate = TemplateTarget(
            defaultOutputPath.resolve("template-tree.txt"),
            TemplateForFreemarker("/ch/senegal/pluginexample/info-template.ftl")
        )
        return setOf(generalTemplate)
    }

    private val entitiesDescriptionFacet = FacetFactory.StringFacetFactory.createFacet(
        facetName = FacetName.of("Description"),
        enclosingConceptName = EntitiesConceptPlugin.conceptName,
    )


    override val facets: Set<Facet> = setOf(
        entitiesDescriptionFacet
    )

}
