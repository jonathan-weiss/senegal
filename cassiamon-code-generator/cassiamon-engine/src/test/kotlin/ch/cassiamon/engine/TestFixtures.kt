package ch.cassiamon.engine

import ch.cassiamon.engine.model.inputsource.ModelInputData
import ch.cassiamon.engine.model.inputsource.ModelInputDataBuilder
import ch.cassiamon.engine.model.types.ConceptIdentifier
import ch.cassiamon.engine.model.types.TextFacetValue
import ch.cassiamon.engine.schema.registration.RegistrationApiDefaultImpl
import ch.cassiamon.engine.schema.types.Schema
import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName

object TestFixtures {
    val databaseTableConceptName = ConceptName.of("DatabaseTable")
    val databaseTableFieldConceptName = ConceptName.of("DatabaseField")
    val databaseTableFieldIndexConceptName = ConceptName.of("DatabaseFieldIndex")
    val tableNameFacetName = FacetName.of("TableName")
    val tableFieldNameFacetName = FacetName.of("FieldName")
    val tableFieldTypeFacetName = FacetName.of("FieldType")
    val tableFieldLengthFacetName = FacetName.of("FieldLength")
    val tableNameAndFieldNameFacetName = FacetName.of("TableNameAndFieldName")
    val tableIndexNameFacetName = FacetName.of("TableIndexName")

    fun createTestFixtureSchema(): Schema {
        val registrationApi = RegistrationApiDefaultImpl()

        // act
        registrationApi.configure {
            newRootConcept(conceptName = databaseTableConceptName) {
                addTextFacet(facetName = tableNameFacetName) { _, value -> value.uppercase() }
            }
            newChildConcept(conceptName = databaseTableFieldConceptName, parentConceptName = databaseTableConceptName) {
                addTextFacet(tableFieldNameFacetName) { _, value -> value.uppercase() }
                addTextFacet(tableFieldTypeFacetName) // TODO use enumeration as soon as available
                addIntegerNumberFacet(tableFieldLengthFacetName, setOf(tableFieldTypeFacetName))
                addCalculatedTextFacet(
                    facetName = tableNameAndFieldNameFacetName,
                    dependingOnFacets = setOf(tableFieldNameFacetName)
                ) { node -> "TODO write <TableName>.<FieldName>" } // TODO write simple code example as soon as nodes have properties

            }
        }

        return registrationApi.provideSchema()
    }

    fun createModelInputData(): ModelInputData {
        val modelInputDataBuilder = ModelInputDataBuilder()

        modelInputDataBuilder.attachConceptData(
            conceptName = ConceptName.of("DatabaseTable"),
            conceptIdentifier = ConceptIdentifier.of("PersonDbTable"),
            facetValues = arrayOf(Pair(FacetName.of("TableName"), TextFacetValue("Person")))
        )

        return modelInputDataBuilder.provideModelInputData()

    }

}
