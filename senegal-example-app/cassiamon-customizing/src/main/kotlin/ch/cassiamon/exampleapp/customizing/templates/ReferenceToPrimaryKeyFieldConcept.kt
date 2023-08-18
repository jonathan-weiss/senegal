package ch.cassiamon.exampleapp.customizing.templates

import ch.cassiamon.api.process.schema.annotations.Concept
import ch.cassiamon.api.process.schema.annotations.Facet

@Concept("ReferenceToPrimaryKeyField")
interface ReferenceToPrimaryKeyFieldConcept: EntityField {

    @Facet("ReferencedPrimaryKeyField")
    fun getReferencedPrimaryKeyField(): PrimaryKeyFieldConcept
}
