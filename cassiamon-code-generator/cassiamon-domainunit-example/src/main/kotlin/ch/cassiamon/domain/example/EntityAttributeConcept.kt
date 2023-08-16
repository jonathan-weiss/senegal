package ch.cassiamon.domain.example

import ch.cassiamon.api.process.schema.annotations.Concept
import ch.cassiamon.api.process.schema.annotations.Facet

@Concept("EntityAttribute")
interface EntityAttributeConcept {
    @Facet("AttributeName")
    fun attributeName(): String

    @Facet("AttributeType")
    fun attributeType(): AttributeTypeEnum

    enum class AttributeTypeEnum {
        TEXT,
        NUMBER,
        BOOLEAN
    }
}
