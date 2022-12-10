package ch.senegal.pluginexample

import ch.senegal.plugin.*

object EntityPurposePlugin : Purpose {
    override val purposeName: PurposeName = PurposeName("Entity")
    override val facets: Set<Facet> = setOf(EntityNameFacet)
}

object EntityNameFacet : Facet {
    override val facetName: FacetName = FacetName("Name")
    override val enclosingConceptName: ConceptName = EntityConceptPlugin.conceptName
    override val facetType: FacetType = TextFacetType
}

