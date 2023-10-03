package ch.cassiamon.exampleapp.customizing.templates.db

import ch.cassiamon.exampleapp.customizing.templates.PrimaryKeyFieldConcept
import ch.cassiamon.exampleapp.customizing.templates.kotlinmodel.KotlinModelField

class PrimaryKeyDbField(model: PrimaryKeyFieldConcept, dbTable: DbTable, kotlinModelField: KotlinModelField): DbField(model, dbTable, kotlinModelField)
