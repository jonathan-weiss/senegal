package ch.senegal.example

import ch.senegal.engine.util.CaseUtil
import ch.senegal.plugin.*
import ch.senegal.plugin.factory.FacetFactory
import ch.senegal.plugin.model.ModelNode
import java.nio.file.Path


object AngularComponentPurposePlugin : Purpose {
    override val purposeName: PurposeName = PurposeName.of("AngularComponent")

    val angularComponentBasePathFacet = FacetFactory.DirectoryFacetFactory.createFacet(
        facetName = FacetName.of("BasePath"),
        enclosingConceptName = EntitiesConceptPlugin.conceptName
    )

    val angularComponentSuffixName = FacetFactory.StringFacetFactory.createCalculatedFacet(
        facetName = FacetName.of("SuffixName"),
        enclosingConceptName = EntityConceptPlugin.conceptName
    ) { modelNode: ModelNode -> modelNode
        .getStringFacetValue(EntityPurposePlugin.purposeName, EntityPurposePlugin.entityNameFacet.facetName)
        ?.let { CaseUtil.decapitalize(it) }
    }

    val angularComponentCapitalizeSuffixName = FacetFactory.StringFacetFactory.createCalculatedFacet(
        facetName = FacetName.of("CapitalizeSuffixName"),
        enclosingConceptName = EntityConceptPlugin.conceptName
    ) { modelNode: ModelNode -> modelNode
        .getStringFacetValue(EntityPurposePlugin.purposeName, EntityPurposePlugin.entityNameFacet.facetName)
    }

    val angularComponentSuffixFileName = FacetFactory.StringFacetFactory.createCalculatedFacet(
        facetName = FacetName.of("SuffixFileName"),
        enclosingConceptName = EntityConceptPlugin.conceptName
    ) { modelNode: ModelNode -> modelNode
        .getStringFacetValue(EntityPurposePlugin.purposeName, EntityPurposePlugin.entityNameFacet.facetName)
        ?.let { CaseUtil.camelToDashCase(it) }
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

        val angularComponentBasePath = modelNode.parentModelNode()?.getDirectoryFacetValue(purposeName, angularComponentBasePathFacet.facetName)
        val angularComponentSuffixFilename = modelNode.getStringFacetValue(purposeName, angularComponentSuffixFileName.facetName)

        if(angularComponentBasePath != null && angularComponentSuffixFilename != null) {
            targets.add(TemplateTarget(angularComponentBasePath.resolve("component/${angularComponentSuffixFilename}/${angularComponentSuffixFilename}-panel-view/${angularComponentSuffixFilename}-panel-view.component.ts"), "/ch/senegal/pluginexample/angular-component-panel-view-ts.ftl"))
            targets.add(TemplateTarget(angularComponentBasePath.resolve("component/${angularComponentSuffixFilename}/${angularComponentSuffixFilename}-panel-view/${angularComponentSuffixFilename}-panel-view.component.scss"), "/ch/senegal/pluginexample/angular-component-panel-view-scss.ftl"))
            targets.add(TemplateTarget(angularComponentBasePath.resolve("component/${angularComponentSuffixFilename}/${angularComponentSuffixFilename}-panel-view/${angularComponentSuffixFilename}-panel-view.component.html"), "/ch/senegal/pluginexample/angular-component-panel-view-html.ftl"))
        }

        return targets
    }

    override val facets: Set<Facet> = setOf(
        angularComponentBasePathFacet,
        angularComponentSuffixName,
        angularComponentCapitalizeSuffixName,
        angularComponentSuffixFileName,
    )

}
