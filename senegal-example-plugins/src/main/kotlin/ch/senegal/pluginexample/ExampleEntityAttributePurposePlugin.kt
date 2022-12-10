package ch.senegal.pluginexample

import ch.senegal.plugin.*

object EntityAttributePurposePlugin : Purpose {
    override val purposeName: PurposeName = PurposeName("EntityAttribute")
    override val facets: Set<Facet> = setOf(EntityAttributeNameFacet, EntityAttributeTypeFacet)
}

object EntityAttributeTypeFacet : Facet {
    override val facetName: FacetName = FacetName("Type")
    override val enclosingConceptName: ConceptName = EntityAttributeConceptPlugin.conceptName
    override val facetType: FacetType = EnumerationFacetType(
        listOf(
            FacetTypeEnumerationValue("TEXT"),
            FacetTypeEnumerationValue("NUMBER"),
            FacetTypeEnumerationValue("BOOLEAN")
        )
    )

}

object EntityAttributeNameFacet : Facet {
    override val facetName: FacetName = FacetName("Name")
    override val enclosingConceptName: ConceptName = EntityAttributeConceptPlugin.conceptName
    override val facetType: FacetType = TextFacetType
}
