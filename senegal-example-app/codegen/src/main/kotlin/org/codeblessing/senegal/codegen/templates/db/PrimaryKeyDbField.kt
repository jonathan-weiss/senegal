package org.codeblessing.senegal.codegen.templates.db

import org.codeblessing.senegal.codegen.schema.PrimaryKeyFieldConcept
import org.codeblessing.senegal.codegen.templates.kotlinmodel.KotlinModelField

class PrimaryKeyDbField(model: PrimaryKeyFieldConcept, dbTable: DbTable, kotlinModelField: KotlinModelField): DbField(model, dbTable, kotlinModelField)
