package ch.cassiamon.exampleapp.customizing.templates

import org.codeblessing.sourceamazing.api.process.schema.annotations.Concept

@Concept("PrimaryKeyField", minOccurrence = 1, maxOccurrence = 1)
interface PrimaryKeyFieldConcept: EntityField
