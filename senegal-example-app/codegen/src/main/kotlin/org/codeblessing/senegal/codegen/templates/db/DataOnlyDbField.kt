package org.codeblessing.senegal.codegen.templates.db

import org.codeblessing.senegal.codegen.schema.DataOnlyFieldConcept
import org.codeblessing.senegal.codegen.templates.kotlinmodel.KotlinModelField

class DataOnlyDbField(model: DataOnlyFieldConcept, dbTable: DbTable, kotlinModelField: KotlinModelField): DbField(model, dbTable, kotlinModelField)
