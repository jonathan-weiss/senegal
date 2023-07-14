package ch.cassiamon.exampleapp.customizing.templates

import ch.cassiamon.api.process.schema.annotations.Concept
import ch.cassiamon.api.process.schema.annotations.Facet

@Concept("EntitySimpleAttribute")
interface EntitySimpleAttributeConcept {
    @Facet("EntityAttributeName")
    fun getName(): String

    @Facet("EntityAttributeType")
    fun getType(): String
}
