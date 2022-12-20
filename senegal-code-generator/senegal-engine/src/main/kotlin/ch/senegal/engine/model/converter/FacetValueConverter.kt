package ch.senegal.engine.model.converter

import ch.senegal.plugin.*
import ch.senegal.engine.model.FacetValue
import kotlin.reflect.KClass

enum class FacetValueConverter(val facet: KClass<out Facet>,
                               val converter: StringValueToFacetValueConverter) {
    STRING(StringFacet::class, StringFacetConverter),
    STRING_ENUM(StringEnumerationFacet::class, StringFacetConverter),
    BOOLEAN(BooleanFacet::class, BooleanFacetConverter),
    INTEGER(IntegerFacet::class, IntegerFacetConverter),
    FILE(FileFacet::class, FileFacetConverter),
    DIRECTORY(DirectoryFacet::class, DirectoryFacetConverter),
    ;
    companion object {
        private fun findEnumByFacet(facet: Facet): FacetValueConverter {
            return values().firstOrNull { it.facet == facet::class }
                ?: throw IllegalArgumentException("No Value Converter found for facet $facet.")
        }

        fun convertString(facet: Facet, stringValue: String): FacetValue {
            return findEnumByFacet(facet).converter.convertStringValue(facet, stringValue)
        }
    }
}
