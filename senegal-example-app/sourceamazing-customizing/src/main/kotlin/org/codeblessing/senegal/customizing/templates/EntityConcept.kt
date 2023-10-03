package org.codeblessing.senegal.customizing.templates

import org.codeblessing.sourceamazing.api.process.schema.annotations.ChildConcepts
import org.codeblessing.sourceamazing.api.process.schema.annotations.ChildConceptsWithCommonBaseInterface
import org.codeblessing.sourceamazing.api.process.schema.annotations.Concept
import org.codeblessing.sourceamazing.api.process.schema.annotations.Facet

@Concept("Entity")
interface EntityConcept {

    @Facet("EntityName")
    fun getName(): String

    @ChildConceptsWithCommonBaseInterface(org.codeblessing.senegal.customizing.templates.EntityField::class, [org.codeblessing.senegal.customizing.templates.DataOnlyFieldConcept::class, org.codeblessing.senegal.customizing.templates.ReferenceToPrimaryKeyFieldConcept::class])
    fun entityFields(): List<org.codeblessing.senegal.customizing.templates.EntityField>

    @ChildConcepts(org.codeblessing.senegal.customizing.templates.DataOnlyFieldConcept::class)
    fun entityDataOnlyFields(): List<org.codeblessing.senegal.customizing.templates.DataOnlyFieldConcept>

    @ChildConcepts(org.codeblessing.senegal.customizing.templates.ReferenceToPrimaryKeyFieldConcept::class)
    fun entityReferences(): List<org.codeblessing.senegal.customizing.templates.ReferenceToPrimaryKeyFieldConcept>

    @ChildConcepts(org.codeblessing.senegal.customizing.templates.PrimaryKeyFieldConcept::class)
    fun primaryKeys(): List<org.codeblessing.senegal.customizing.templates.PrimaryKeyFieldConcept>
}
