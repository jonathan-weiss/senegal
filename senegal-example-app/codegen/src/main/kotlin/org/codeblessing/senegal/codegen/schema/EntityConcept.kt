package org.codeblessing.senegal.codegen.schema

import org.codeblessing.sourceamazing.schema.api.annotations.Concept
import org.codeblessing.sourceamazing.schema.api.annotations.QueryFacetValue
import org.codeblessing.sourceamazing.schema.api.annotations.ReferenceFacet
import org.codeblessing.sourceamazing.schema.api.annotations.StringFacet

@Concept([
    EntityConcept.EntityName::class,
    EntityConcept.EntityFields::class,
])
interface EntityConcept {

    @StringFacet
    interface EntityName

    @ReferenceFacet(
        minimumOccurrences = 1,
        maximumOccurrences = Int.MAX_VALUE,
        referencedConcepts = [
            PrimaryKeyFieldConcept::class,
            DataOnlyFieldConcept::class,
            ReferenceToPrimaryKeyFieldConcept::class,
        ])
    interface EntityFields


    @QueryFacetValue(EntityName::class)
    fun getName(): String

    @QueryFacetValue(EntityFields::class)
    fun entityFields(): List<EntityField>

//    @QueryFacetValue(EntityFields::class)
//    fun entityDataOnlyFields(): List<DataOnlyFieldConcept>
//
//    @QueryFacetValue(EntityFields::class)
//    fun entityReferences(): List<ReferenceToPrimaryKeyFieldConcept>
//
//    @QueryFacetValue(EntityFields::class)
//    fun primaryKeys(): List<PrimaryKeyFieldConcept>
}
