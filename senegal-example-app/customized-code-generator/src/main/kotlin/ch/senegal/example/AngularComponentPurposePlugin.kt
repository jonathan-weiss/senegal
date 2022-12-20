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
        val targets: MutableSet<TemplateTarget> = mutableSetOf()
        val angularFrontendBasePath = modelNode.getDirectoryFacetValue(AngularFrontendPurposePlugin.purposeName, AngularFrontendPurposePlugin.angularFrontendBasePathFacet.facetName)

        if(angularFrontendBasePath != null) {
            targets.add(TemplateTarget(angularFrontendBasePath.resolve("generated-entities.module.ts"), "/ch/senegal/pluginexample/angular-component-generated-entities-module-ts.ftl"))
            targets.add(TemplateTarget(angularFrontendBasePath.resolve("generated-entities-routing.module.ts"), "/ch/senegal/pluginexample/angular-component-generated-entities-routing-module-ts.ftl"))
        }

        return targets
    }

    private fun createEntityTemplates(modelNode: ModelNode): Set<TemplateTarget> {
        val targets: MutableSet<TemplateTarget> = mutableSetOf()

        val angularFrontendBasePath = modelNode.parentModelNode()?.getDirectoryFacetValue(AngularFrontendPurposePlugin.purposeName, AngularFrontendPurposePlugin.angularFrontendBasePathFacet.facetName)
        val entityFileName = modelNode.getStringFacetValue(AngularFrontendPurposePlugin.purposeName, AngularFrontendPurposePlugin.angularFrontendEntityFileNameFacet.facetName)

        if(angularFrontendBasePath != null && entityFileName != null) {
            targets.add(TemplateTarget(angularFrontendBasePath.resolve("${entityFileName}/component/${entityFileName}-panel-view/${entityFileName}-panel-view.component.ts"), "/ch/senegal/pluginexample/angular-component-panel-view-ts.ftl"))
            targets.add(TemplateTarget(angularFrontendBasePath.resolve("${entityFileName}/component/${entityFileName}-panel-view/${entityFileName}-panel-view.component.scss"), "/ch/senegal/pluginexample/angular-component-panel-view-scss.ftl"))
            targets.add(TemplateTarget(angularFrontendBasePath.resolve("${entityFileName}/component/${entityFileName}-panel-view/${entityFileName}-panel-view.component.html"), "/ch/senegal/pluginexample/angular-component-panel-view-html.ftl"))

            targets.add(TemplateTarget(angularFrontendBasePath.resolve("${entityFileName}/component/${entityFileName}-table-view/${entityFileName}-table-view.component.ts"), "/ch/senegal/pluginexample/angular-component-table-view-ts.ftl"))
            targets.add(TemplateTarget(angularFrontendBasePath.resolve("${entityFileName}/component/${entityFileName}-table-view/${entityFileName}-table-view.component.scss"), "/ch/senegal/pluginexample/angular-component-table-view-scss.ftl"))
            targets.add(TemplateTarget(angularFrontendBasePath.resolve("${entityFileName}/component/${entityFileName}-table-view/${entityFileName}-table-view.component.html"), "/ch/senegal/pluginexample/angular-component-table-view-html.ftl"))

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
