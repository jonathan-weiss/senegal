package ch.senegal.plugin

sealed class DecorType

object TextDecorType : DecorType()

object IntegerNumberDecorType : DecorType()

object BooleanDecorType : DecorType()

class EnumerationDecorType(val enumerationValues: List<DecorTypeEnumerationValue>) : DecorType()


@JvmInline
value class DecorTypeEnumerationValue(val name: String)
