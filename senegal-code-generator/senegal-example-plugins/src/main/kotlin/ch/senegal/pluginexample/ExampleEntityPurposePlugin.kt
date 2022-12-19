package ch.senegal.pluginexample

import ch.senegal.plugin.*
import ch.senegal.plugin.factory.FacetFactory

object EntityPurposePlugin : Purpose {
    override val purposeName: PurposeName = PurposeName.of("Entity")
    override val facets: Set<Facet> = setOf(
        FacetFactory.StringFacetFactory.createFacet(
            facetName = FacetName.of("Name"),
            enclosingConceptName = EntityConceptPlugin.conceptName,
        )

    )
}

