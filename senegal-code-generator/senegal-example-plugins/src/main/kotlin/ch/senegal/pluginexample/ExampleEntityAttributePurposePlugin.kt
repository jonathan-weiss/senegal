package ch.senegal.pluginexample

import ch.senegal.plugin.*
import ch.senegal.plugin.factory.FacetFactory

object EntityAttributePurposePlugin : Purpose {
    val textFieldOption = StringEnumerationFacetOption("TEXT")
    val numberFieldOption = StringEnumerationFacetOption("NUMBER")
    val booleanFieldOption = StringEnumerationFacetOption("BOOLEAN")

    val nameFacetName = FacetName.of("Name")
    val typeFacetName = FacetName.of("Type")

    override val purposeName: PurposeName = PurposeName.of("EntityAttribute")
    override val facets: Set<Facet> = setOf(
        FacetFactory.StringFacetFactory.createFacet(
            facetName = nameFacetName,
            enclosingConceptName = EntityAttributeConceptPlugin.conceptName,
        ),
        FacetFactory.StringEnumerationFacetFactory.createFacet(
            facetName = typeFacetName,
            enclosingConceptName = EntityAttributeConceptPlugin.conceptName,
            enumerationOptions = listOf(textFieldOption, numberFieldOption, booleanFieldOption),
        ),
    )
}
