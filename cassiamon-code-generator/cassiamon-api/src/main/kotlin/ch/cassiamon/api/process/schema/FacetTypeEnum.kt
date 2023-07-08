package ch.cassiamon.api.process.schema

import kotlin.reflect.KClass

enum class FacetTypeEnum(val typeClass: KClass<*>) {
    TEXT(String::class),
    NUMBER(Long::class),
    BOOLEAN(Boolean::class);

    fun isCompatibleType(facetValue: Any): Boolean {
        return matchingEnumByTypeClass(facetValue::class)
            ?.let { matchingEnum -> matchingEnum == this }
            ?: false
    }

    companion object {
        fun matchingEnumByTypeClass(classType: KClass<*>): FacetTypeEnum? {
            return when(classType) {
                String::class -> TEXT
                Int::class -> NUMBER // TODO is the cast to Int done properly in case of Long values?
                Long::class -> NUMBER
                Boolean::class -> BOOLEAN
                else -> null
            }
        }
    }
}
