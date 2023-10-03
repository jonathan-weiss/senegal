package org.codeblessing.senegal.customizing.templates

import org.codeblessing.sourceamazing.api.process.schema.annotations.Concept

@Concept("PrimaryKeyField", minOccurrence = 1, maxOccurrence = 1)
interface PrimaryKeyFieldConcept: org.codeblessing.senegal.customizing.templates.EntityField
