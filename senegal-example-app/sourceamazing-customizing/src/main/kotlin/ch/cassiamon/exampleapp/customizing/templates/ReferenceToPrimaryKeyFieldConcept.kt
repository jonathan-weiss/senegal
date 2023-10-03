package ch.cassiamon.exampleapp.customizing.templates

import org.codeblessing.sourceamazing.api.process.schema.annotations.Concept
import org.codeblessing.sourceamazing.api.process.schema.annotations.Facet

@Concept("ReferenceToPrimaryKeyField")
interface ReferenceToPrimaryKeyFieldConcept: EntityField {

    @Facet("ReferencedPrimaryKeyField")
    fun getReferencedPrimaryKeyField(): PrimaryKeyFieldConcept
}
