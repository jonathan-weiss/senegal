package ch.senegal.plugin

sealed interface DecorType

object TextDecorType : DecorType

object IntegerNumberDecorType : DecorType

object BooleanDecorType : DecorType

object FileDecorType : DecorType

object DirectoryDecorType : DecorType

class EnumerationDecorType(val enumerationValues: List<DecorTypeEnumerationValue>) : DecorType


@JvmInline
value class DecorTypeEnumerationValue(val name: String)
