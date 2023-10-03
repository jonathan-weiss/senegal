package ch.cassiamon.exampleapp.customizing.templates

import org.codeblessing.sourceamazing.api.process.schema.annotations.ChildConcepts
import org.codeblessing.sourceamazing.api.process.schema.annotations.ChildConceptsWithCommonBaseInterface
import org.codeblessing.sourceamazing.api.process.schema.annotations.Concept
import org.codeblessing.sourceamazing.api.process.schema.annotations.Facet

@Concept("Entity")
interface EntityConcept {

    @Facet("EntityName")
    fun getName(): String

    @ChildConceptsWithCommonBaseInterface(EntityField::class, [DataOnlyFieldConcept::class, ReferenceToPrimaryKeyFieldConcept::class])
    fun entityFields(): List<EntityField>

    @ChildConcepts(DataOnlyFieldConcept::class)
    fun entityDataOnlyFields(): List<DataOnlyFieldConcept>

    @ChildConcepts(ReferenceToPrimaryKeyFieldConcept::class)
    fun entityReferences(): List<ReferenceToPrimaryKeyFieldConcept>

    @ChildConcepts(PrimaryKeyFieldConcept::class)
    fun primaryKeys(): List<PrimaryKeyFieldConcept>
}
