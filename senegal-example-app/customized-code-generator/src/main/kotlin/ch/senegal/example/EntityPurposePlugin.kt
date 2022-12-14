package ch.senegal.example

import ch.senegal.plugin.*
import ch.senegal.plugin.model.ModelNode
import java.nio.file.Path


object EntityPurposePlugin : Purpose {
    override val purposeName: PurposeName = PurposeName.of("Entity")
    override val facets: Set<Facet> = setOf(EntityNameFacet, EntityAttributeNameFacet, EntityAttributeTypeFacet)
}
object EntityNameFacet : Facet {
    override val facetName: FacetName = FacetName.of("Name")
    override val enclosingConceptName = EntityConceptPlugin.conceptName
    override val facetType: FacetType = TextFacetType
}

object EntityAttributeNameFacet : Facet {
    override val facetName: FacetName = FacetName.of("AttributeName")
    override val enclosingConceptName = EntityAttributeConceptPlugin.conceptName
    override val facetType: FacetType = TextFacetType
}

object EntityAttributeTypeFacet : Facet {
    override val facetName: FacetName = FacetName.of("AttributeType")
    override val enclosingConceptName = EntityAttributeConceptPlugin.conceptName
    override val facetType: FacetType = EnumerationFacetType(
        listOf(
            FacetTypeEnumerationValue("Text"),
            FacetTypeEnumerationValue("IntegerNumber"),
            FacetTypeEnumerationValue("Boolean"),
        )
    )
}
