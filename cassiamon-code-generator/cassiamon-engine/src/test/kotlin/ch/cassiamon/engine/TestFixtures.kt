package ch.cassiamon.engine

import ch.cassiamon.engine.model.ConceptIdentifier
import ch.cassiamon.engine.model.ConceptInputEntry
import ch.cassiamon.engine.model.FacetInputEntry
import ch.cassiamon.engine.schema.WiredConceptSchema
import ch.cassiamon.engine.schema.WiredFacetSchema
import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName
import ch.cassiamon.pluginapi.factory.ConceptSchemaFactory
import ch.cassiamon.pluginapi.factory.FacetSchemaFactory

object TestFixtures {
    val databaseTableConceptName = ConceptName.of("DatabaseTable")
    val databaseTableFieldConceptName = ConceptName.of("DatabaseField")
    val tableNameFacetName = FacetName.of("TableName")
    val tableFieldNameFacetName = FacetName.of("FieldName")
    val tableFieldTypeFacetName = FacetName.of("FieldType")
    val tableFieldLengthFacetName = FacetName.of("FieldLength")

    val databaseTableConceptSchema = ConceptSchemaFactory.createConceptSchema(databaseTableConceptName)
    val tableNameFacetSchema = FacetSchemaFactory.StringFacetSchemaFactory.createFacet(tableNameFacetName, databaseTableConceptName)

    val databaseTableFieldConceptSchema = ConceptSchemaFactory.createConceptSchema(databaseTableFieldConceptName)
    val tableFieldNameFacetSchema = FacetSchemaFactory.StringFacetSchemaFactory.createFacet(tableFieldNameFacetName, databaseTableFieldConceptName)
    val tableFieldTypeFacetSchema = FacetSchemaFactory.StringFacetSchemaFactory.createFacet(tableFieldTypeFacetName, databaseTableFieldConceptName)
    val tableFieldLengthFacetSchema = FacetSchemaFactory.IntegerFacetSchemaFactory.createFacet(tableFieldLengthFacetName, databaseTableFieldConceptName)

    val tableFieldNameWiredFacetSchema = WiredFacetSchema(tableFieldNameFacetSchema)
    val tableFieldTypeWiredFacetSchema = WiredFacetSchema(tableFieldTypeFacetSchema)
    val tableFieldLengthWiredFacetSchema = WiredFacetSchema(tableFieldLengthFacetSchema)
    val tableNameWiredFacetSchema = WiredFacetSchema(tableNameFacetSchema)

    val databaseTableWiredConceptSchema = WiredConceptSchema(databaseTableConceptSchema,
        setOf(
            tableNameWiredFacetSchema
        )
    )

    val databaseTableFieldWiredConceptSchema = WiredConceptSchema(databaseTableFieldConceptSchema,
        setOf(
            tableFieldNameWiredFacetSchema,
            tableFieldTypeWiredFacetSchema,
            tableFieldLengthWiredFacetSchema
        )
    )

    val wiredConceptSchemas = setOf(
        databaseTableWiredConceptSchema,
        databaseTableFieldWiredConceptSchema
    )

    val personTableIdentifier: ConceptIdentifier = ConceptIdentifier.of("Person")
    val personFirstnameTableFieldIdentifier: ConceptIdentifier = ConceptIdentifier.of("PersonFirstname")
    val personLastnameTableFieldIdentifier: ConceptIdentifier = ConceptIdentifier.of("PersonLastname")

    val addressTableIdentifier: ConceptIdentifier = ConceptIdentifier.of("Address")
    val addressStreetTableFieldIdentifier: ConceptIdentifier = ConceptIdentifier.of("AddressStreet")

    val addressStreetFacetInputEntry: FacetInputEntry = FacetInputEntry(
        wiredFacetSchema = tableFieldNameWiredFacetSchema
    )
    val addressConceptInputEntry: ConceptInputEntry = ConceptInputEntry(
        wiredConceptSchema = databaseTableWiredConceptSchema,
        conceptIdentifier = addressTableIdentifier,
        facetInputEntries = setOf(addressStreetFacetInputEntry)

    )

}
