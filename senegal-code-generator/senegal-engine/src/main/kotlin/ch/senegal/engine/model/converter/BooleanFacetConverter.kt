package ch.senegal.engine.model.converter

import ch.senegal.plugin.Facet
import ch.senegal.plugin.model.FacetValue

object BooleanFacetConverter: StringValueToFacetValueConverter {

    override fun convertStringValue(facet: Facet, stringValue: String): FacetValue {
        return FacetValue.of(stringValue.toBoolean())
    }
}
