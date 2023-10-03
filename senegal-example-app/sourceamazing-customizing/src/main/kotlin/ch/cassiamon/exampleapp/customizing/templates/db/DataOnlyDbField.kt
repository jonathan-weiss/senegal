package ch.cassiamon.exampleapp.customizing.templates.db

import ch.cassiamon.exampleapp.customizing.templates.DataOnlyFieldConcept
import ch.cassiamon.exampleapp.customizing.templates.kotlinmodel.KotlinModelField

class DataOnlyDbField(model: DataOnlyFieldConcept, dbTable: DbTable, kotlinModelField: KotlinModelField): DbField(model, dbTable, kotlinModelField)
