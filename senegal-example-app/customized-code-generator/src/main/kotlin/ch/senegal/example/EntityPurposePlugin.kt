package ch.senegal.example

import ch.senegal.plugin.*
import ch.senegal.plugin.factory.FacetFactory


object EntityPurposePlugin : Purpose {
    override val purposeName: PurposeName = PurposeName.of("Entity")

    val entityNameFacet = FacetFactory.StringFacetFactory.createFacet(
        facetName = FacetName.of("Name"),
        enclosingConceptName = EntityConceptPlugin.conceptName
    )
    val entityAttributeNameFacet = FacetFactory.StringFacetFactory.createFacet(
        facetName = FacetName.of("AttributeName"),
        enclosingConceptName = EntityAttributeConceptPlugin.conceptName
    )

    val entityAttributeTextType = StringEnumerationFacetOption("Text")
    val entityAttributeIntegerNumberType = StringEnumerationFacetOption("IntegerNumber")
    val entityAttributeBooleanType = StringEnumerationFacetOption("Boolean")

    val entityAttributeTypeFacet = FacetFactory.StringEnumerationFacetFactory.createFacet(
        facetName = FacetName.of("AttributeType"),
        enclosingConceptName = EntityAttributeConceptPlugin.conceptName,
        enumerationOptions = listOf(entityAttributeTextType, entityAttributeIntegerNumberType, entityAttributeBooleanType)
    )

    override val facets: Set<Facet> = setOf(entityNameFacet, entityAttributeNameFacet, entityAttributeTypeFacet)
}
