package ch.senegal.pluginexample

import ch.senegal.plugin.*
import ch.senegal.plugin.factory.FacetFactory

object EntityAttributePurposePlugin : Purpose {
    val textFieldOption = StringEnumerationFacetOption("TEXT")
    val numberFieldOption = StringEnumerationFacetOption("NUMBER")
    val booleanFieldOption = StringEnumerationFacetOption("BOOLEAN")


    override val purposeName: PurposeName = PurposeName.of("EntityAttribute")
    override val facets: Set<Facet> = setOf(
        FacetFactory.StringFacetFactory.createFacet(
            facetName = FacetName.of("Name"),
            enclosingConceptName = EntityAttributeConceptPlugin.conceptName,
        ),
        FacetFactory.StringEnumerationFacetFactory.createFacet(
            facetName = FacetName.of("Type"),
            enclosingConceptName = EntityAttributeConceptPlugin.conceptName,
            enumerationOptions = listOf(textFieldOption, numberFieldOption, booleanFieldOption),
        ),
    )
}
