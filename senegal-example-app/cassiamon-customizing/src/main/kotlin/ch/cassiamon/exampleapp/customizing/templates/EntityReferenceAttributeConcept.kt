package ch.cassiamon.exampleapp.customizing.templates

import ch.cassiamon.api.process.schema.annotations.Concept
import ch.cassiamon.api.process.schema.annotations.Facet

@Concept("EntityReferenceAttribute")
interface EntityReferenceAttributeConcept {
    @Facet("EntityReferenceName")
    fun getName(): String

    @Facet("ReferencedEntity")
    fun getReferencedEntity(): EntityConcept
}
