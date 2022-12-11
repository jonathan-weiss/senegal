package ch.senegal.plugin

import ch.senegal.plugin.model.FacetValue
import java.nio.file.Paths

sealed interface FacetType {
    fun facetValueFromString(stringValue: String): FacetValue
}

object TextFacetType : FacetType {
    override fun facetValueFromString(stringValue: String): FacetValue {
        return FacetValue.of(stringValue)
    }
}

object IntegerNumberFacetType : FacetType {
    override fun facetValueFromString(stringValue: String): FacetValue {
        return FacetValue.of(stringValue.toInt())
    }
}

object BooleanFacetType : FacetType {
    override fun facetValueFromString(stringValue: String): FacetValue {
        return FacetValue.of(stringValue.toBoolean())
    }
}

object FileFacetType : FacetType {
    override fun facetValueFromString(stringValue: String): FacetValue {
        return FacetValue.of(Paths.get(stringValue))
    }
}

object DirectoryFacetType : FacetType {
    override fun facetValueFromString(stringValue: String): FacetValue {
        return FacetValue.of(Paths.get(stringValue))
    }
}

class EnumerationFacetType(val enumerationValues: List<FacetTypeEnumerationValue>) : FacetType {
    override fun facetValueFromString(stringValue: String): FacetValue {
        if(!enumerationValues.map { it.name }.contains(stringValue)) {
            throw IllegalArgumentException("The value '$stringValue' is not contained in ${enumerationValues.map { it.name }}")
        }
        return FacetValue.of(stringValue)
    }
}


@JvmInline
value class FacetTypeEnumerationValue(val name: String)
