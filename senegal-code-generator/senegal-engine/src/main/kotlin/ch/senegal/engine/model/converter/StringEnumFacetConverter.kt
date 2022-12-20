package ch.senegal.engine.model.converter

import ch.senegal.plugin.Facet
import ch.senegal.plugin.StringEnumerationFacet
import ch.senegal.engine.model.FacetValue

object StringEnumFacetConverter: StringValueToFacetValueConverter {

    override fun convertStringValue(facet: Facet, stringValue: String): FacetValue {
        throwIfNotValidEnumerationValue(facet, stringValue)
        return FacetValue.of(stringValue)
    }

    fun throwIfNotValidEnumerationValue(facet: Facet, stringValue: String) {
        if(facet !is StringEnumerationFacet) {
            throw IllegalArgumentException("Facet for Enumeration was not type 'StringEnumerationFacet' but '$facet'.")
        }
        val validOptions = facet.enumerationOptions.map { it.name }
        if(!validOptions.contains(stringValue)) {
            throw IllegalArgumentException("The value '$stringValue' is not one of the valid options $validOptions.")
        }
    }


}
