package ch.cassiamon.api.process.schema

import ch.cassiamon.api.process.schema.annotations.Concept
import kotlin.reflect.KClass

enum class FacetTypeEnum {
    TEXT,
    NUMBER,
    BOOLEAN,
    REFERENCE,
    ;

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
                else -> if (referencedTypeConceptName(classType) != null) REFERENCE else null
            }
        }

        fun referencedTypeConceptName(clazzType: KClass<*>): ConceptName? {
            return clazzType.java.getAnnotation(Concept::class.java)?.let { ConceptName.of(it.conceptName) }
        }
    }
}
