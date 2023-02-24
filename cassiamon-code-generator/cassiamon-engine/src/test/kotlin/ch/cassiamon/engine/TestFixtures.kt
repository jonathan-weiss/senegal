package ch.cassiamon.engine

import ch.cassiamon.engine.inputsource.ModelInputData
import ch.cassiamon.engine.inputsource.ModelInputDataCollector
import ch.cassiamon.engine.model.types.TextFacetValue
import ch.cassiamon.engine.schema.registration.RegistrationApiDefaultImpl
import ch.cassiamon.engine.schema.types.Schema
import ch.cassiamon.pluginapi.*
import ch.cassiamon.pluginapi.model.ConceptIdentifier
import ch.cassiamon.pluginapi.registration.TemplateFunction
import ch.cassiamon.pluginapi.template.TargetGeneratedFileWithModel
import ch.cassiamon.pluginapi.template.TemplateRenderer
import ch.cassiamon.pluginapi.template.helper.StringContentByteIterator
import java.nio.file.Paths

object TestFixtures {
    val databaseTableConceptName = ConceptName.of("DatabaseTable")
    val databaseTableFieldConceptName = ConceptName.of("DatabaseField")
    val databaseTableFieldIndexConceptName = ConceptName.of("DatabaseFieldIndex")
    val tableNameFacetName = NameOfMandatoryTextFacet.of("TableName")
    val tableFieldNameFacetName = NameOfMandatoryTextFacet.of("FieldName")
    val tableFieldTypeFacetName = NameOfMandatoryTextFacet.of("FieldType")
    val tableFieldLengthFacetName = NameOfMandatoryIntegerNumberFacet.of("FieldLength")
    val tableFieldForeignKeyConceptIdFacetName = NameOfOptionalConceptReferenceFacet.of("FieldForeignKey")
    val tableNameAndFieldNameFacetName = NameOfMandatoryTextFacet.of("TableNameAndFieldName")
    val tableIndexNameFacetName = NameOfMandatoryTextFacet.of("TableIndexName")

    fun createTestFixtureSchema(registrationApi: RegistrationApiDefaultImpl = RegistrationApiDefaultImpl()): Schema {

        registrationApi.configureSchema {
            newRootConcept(conceptName = databaseTableConceptName) {
                addTextFacet(facetName = tableNameFacetName)

                newChildConcept(conceptName = databaseTableFieldConceptName) {
                    addTextFacet(facetName = tableFieldNameFacetName)
                    addTextFacet(facetName = tableFieldTypeFacetName) // TODO use enumeration as soon as available
                    addIntegerNumberFacet(facetName = tableFieldLengthFacetName)
                    addCalculatedTextFacet(facetName = tableNameAndFieldNameFacetName
                    ) { node -> "TODO write <TableName>.<FieldName>" } // TODO write simple code example as soon as nodes have properties

                    addConceptReferenceFacet(tableFieldForeignKeyConceptIdFacetName, databaseTableConceptName)
                    newChildConcept(conceptName = databaseTableFieldIndexConceptName) {
                        addTextFacet(tableIndexNameFacetName)
                    }


                }
            }
        }

        return registrationApi.provideSchema()
    }

    fun createTestTemplates(registrationApi: RegistrationApiDefaultImpl = RegistrationApiDefaultImpl()): List<TemplateFunction> {
        registrationApi.configureTemplates {
            // test a file generation for all nodes
            newTemplate { templateNodesProvider ->

                val templateNodes = templateNodesProvider
                    .conceptModelNodesByConceptName(databaseTableConceptName)

                val tagetGeneratedFiles = templateNodes
                    .map { templateNode -> TargetGeneratedFileWithModel(Paths.get("db_${templateNode.facetValues.asString(tableNameFacetName)}.create.sql"), templateNodes) }
                    .toSet()


                return@newTemplate TemplateRenderer(tagetGeneratedFiles) { targetGeneratedFileWithModel: TargetGeneratedFileWithModel ->
                    return@TemplateRenderer StringContentByteIterator(
                        """
                           -- content of ${targetGeneratedFileWithModel.targetFile}
                           CREATE TABLE ${targetGeneratedFileWithModel.model.first().facetValues.asString(tableNameFacetName)} ;
                           --
                        """.trimIndent()
                    )
                }
            }

            // test a single node file generation
            newTemplate { templateNodesProvider ->

                val templateNodes = templateNodesProvider
                    .conceptModelNodesByConceptName(databaseTableConceptName)

                return@newTemplate TemplateRenderer(setOf(TargetGeneratedFileWithModel(Paths.get("index.sql"), templateNodes))) { targetGeneratedFileWithModel: TargetGeneratedFileWithModel ->
                    return@TemplateRenderer StringContentByteIterator(
                        """
                           -- content of ${targetGeneratedFileWithModel.targetFile}
                           ${templateNodes.joinToString("\n") { "-- ${it.conceptIdentifier.code}: ${it.facetValues.asString(
                            tableNameFacetName)}" }}
                           --
                        """.trimIndent()
                    )
                }
            }

        }

        return registrationApi.provideTemplates()
    }

    fun createModelInputData(): ModelInputData {
        val modelInputDataCollector = ModelInputDataCollector()

        val personTableId = ConceptIdentifier.of("Person")
        modelInputDataCollector.newConceptData(
            conceptName = databaseTableConceptName,
            conceptIdentifier = personTableId,
            parentConceptIdentifier = null,
        ).withFacetValue(tableNameFacetName, TextFacetValue("Person")).attach()

        return modelInputDataCollector.provideModelInputData()
    }
}
