package ch.senegal.engine.model.converter

import ch.senegal.plugin.Facet
import ch.senegal.engine.model.FacetValue

object StringFacetConverter: StringValueToFacetValueConverter {

    override fun convertStringValue(facet: Facet, stringValue: String): FacetValue {
        return FacetValue.of(stringValue)
    }
}
