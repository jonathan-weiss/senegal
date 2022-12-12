package ch.senegal.plugin

import ch.senegal.plugin.model.FacetValue
import java.nio.file.Path
import java.nio.file.Paths

sealed interface FacetType {
    fun facetValueFromString(stringValue: String): FacetValue
    fun isValidateFacetValue(facetValue: FacetValue): Boolean
}

object TextFacetType : FacetType {
    override fun facetValueFromString(stringValue: String): FacetValue {
        return FacetValue.of(stringValue)
    }

    override fun isValidateFacetValue(facetValue: FacetValue): Boolean {
        return facetValue.value is String
    }
}

object IntegerNumberFacetType : FacetType {
    override fun facetValueFromString(stringValue: String): FacetValue {
        return FacetValue.of(stringValue.toInt())
    }


    override fun isValidateFacetValue(facetValue: FacetValue): Boolean {
        return facetValue.value is Int
    }
}

object BooleanFacetType : FacetType {
    override fun facetValueFromString(stringValue: String): FacetValue {
        return FacetValue.of(stringValue.toBoolean())
    }


    override fun isValidateFacetValue(facetValue: FacetValue): Boolean {
        return facetValue.value is Boolean
    }
}

object FileFacetType : FacetType {
    override fun facetValueFromString(stringValue: String): FacetValue {
        return FacetValue.of(Paths.get(stringValue))
    }

    override fun isValidateFacetValue(facetValue: FacetValue): Boolean {
        return facetValue.value is Path
    }
}

object DirectoryFacetType : FacetType {
    override fun facetValueFromString(stringValue: String): FacetValue {
        return FacetValue.of(Paths.get(stringValue))
    }

    override fun isValidateFacetValue(facetValue: FacetValue): Boolean {
        return facetValue.value is Path
    }
}

class EnumerationFacetType(val enumerationValues: List<FacetTypeEnumerationValue>) : FacetType {
    override fun facetValueFromString(stringValue: String): FacetValue {
        if(!enumerationValuesContains(stringValue)) {
            throw IllegalArgumentException("The value '$stringValue' is not contained in ${enumerationValues.map { it.name }}")
        }
        return FacetValue.of(stringValue)
    }


    override fun isValidateFacetValue(facetValue: FacetValue): Boolean {
        return facetValue.value is String
                && enumerationValuesContains(facetValue.value)
    }

    private fun enumerationValuesContains(stringValue: String): Boolean {
        return enumerationValues.map { it.name }.contains(stringValue)
    }
}


@JvmInline
value class FacetTypeEnumerationValue(val name: String)
