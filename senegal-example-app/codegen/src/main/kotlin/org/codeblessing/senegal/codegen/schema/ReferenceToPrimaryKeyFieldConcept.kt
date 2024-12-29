package org.codeblessing.senegal.codegen.schema

import org.codeblessing.sourceamazing.schema.api.annotations.Concept
import org.codeblessing.sourceamazing.schema.api.annotations.QueryFacetValue
import org.codeblessing.sourceamazing.schema.api.annotations.ReferenceFacet

@Concept(facets = [
    EntityField.FieldName::class,
    ReferenceToPrimaryKeyFieldConcept.ReferencedPrimaryKeyField::class,
])

interface ReferenceToPrimaryKeyFieldConcept: EntityField {

    @ReferenceFacet(referencedConcepts = [PrimaryKeyFieldConcept::class])
    interface ReferencedPrimaryKeyField

    @QueryFacetValue(ReferencedPrimaryKeyField::class)
    fun getReferencedPrimaryKeyField(): PrimaryKeyFieldConcept
}
