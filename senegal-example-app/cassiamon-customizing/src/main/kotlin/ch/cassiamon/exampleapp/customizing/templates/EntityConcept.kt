package ch.cassiamon.exampleapp.customizing.templates

import ch.cassiamon.api.process.schema.annotations.ChildConcepts
import ch.cassiamon.api.process.schema.annotations.ChildConceptsWithCommonBaseInterface
import ch.cassiamon.api.process.schema.annotations.Concept
import ch.cassiamon.api.process.schema.annotations.Facet

@Concept("Entity")
interface EntityConcept {

    @Facet("EntityName")
    fun getName(): String

    @ChildConceptsWithCommonBaseInterface(EntityField::class, [DataOnlyFieldConcept::class])
    fun entityFields(): List<EntityField>

    @ChildConcepts(ReferenceToPrimaryKeyFieldConcept::class)
    fun entityReferences(): List<ReferenceToPrimaryKeyFieldConcept>

    @ChildConcepts(PrimaryKeyFieldConcept::class)
    fun primaryKeys(): List<PrimaryKeyFieldConcept>
}
