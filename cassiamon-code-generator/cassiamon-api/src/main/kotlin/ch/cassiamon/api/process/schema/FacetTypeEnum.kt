package ch.cassiamon.api.process.schema

enum class FacetTypeEnum(val typeClass: Class<*>) {
    TEXT(String::class.java),
    NUMBER(Long::class.java);

    fun isCompatibleType(facetValue: Any): Boolean {
        return typeClass.isAssignableFrom(facetValue.javaClass)
    }
}
