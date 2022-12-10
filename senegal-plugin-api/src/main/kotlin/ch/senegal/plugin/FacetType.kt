package ch.senegal.plugin

sealed interface FacetType

object TextFacetType : FacetType

object IntegerNumberFacetType : FacetType

object BooleanFacetType : FacetType

object FileFacetType : FacetType

object DirectoryFacetType : FacetType

class EnumerationFacetType(val enumerationValues: List<FacetTypeEnumerationValue>) : FacetType


@JvmInline
value class FacetTypeEnumerationValue(val name: String)
