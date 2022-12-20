package ch.senegal.engine.model.converter

import ch.senegal.plugin.Facet
import ch.senegal.engine.model.FacetValue
import java.nio.file.Paths

object FileFacetConverter: StringValueToFacetValueConverter {

    override fun convertStringValue(facet: Facet, stringValue: String): FacetValue {
        return FacetValue.of(Paths.get(stringValue))
    }
}
