package ch.senegal.engine.model.converter

import ch.senegal.plugin.Facet
import ch.senegal.plugin.model.FacetValue

interface StringValueToFacetValueConverter {
    fun convertStringValue(facet: Facet, stringValue: String): FacetValue

}
