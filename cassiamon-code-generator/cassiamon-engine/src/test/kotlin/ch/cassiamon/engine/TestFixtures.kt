package ch.cassiamon.engine

import ch.cassiamon.engine.model.inputsource.ModelInputData
import ch.cassiamon.engine.model.inputsource.ModelInputDataCollector
import ch.cassiamon.engine.model.types.TextFacetValue
import ch.cassiamon.engine.schema.registration.RegistrationApiDefaultImpl
import ch.cassiamon.engine.schema.registration.SchemaRegistrationDefaultImpl
import ch.cassiamon.engine.schema.types.Schema
import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName
import ch.cassiamon.pluginapi.model.ConceptIdentifier
import ch.cassiamon.pluginapi.registration.RegistrationApi
import ch.cassiamon.pluginapi.registration.TemplateFunction
import ch.cassiamon.pluginapi.template.TargetGeneratedFileWithModel
import ch.cassiamon.pluginapi.template.TemplateRenderer
import ch.cassiamon.pluginapi.template.helper.StringContentByteIterator
import java.nio.file.Paths

object TestFixtures {
    val databaseTableConceptName = ConceptName.of("DatabaseTable")
    val databaseTableFieldConceptName = ConceptName.of("DatabaseField")
    val databaseTableFieldForeignKeyConceptName = ConceptName.of("DatabaseFieldForeignKey")
    val databaseTableFieldIndexConceptName = ConceptName.of("DatabaseFieldIndex")
    val tableNameFacetName = FacetName.of("TableName")
    val tableFieldNameFacetName = FacetName.of("FieldName")
    val tableFieldTypeFacetName = FacetName.of("FieldType")
    val tableFieldLengthFacetName = FacetName.of("FieldLength")
    val tableFieldForeignKeyConceptIdFacetName = FacetName.of("FieldForeignKey")
    val tableNameAndFieldNameFacetName = FacetName.of("TableNameAndFieldName")
    val tableIndexNameFacetName = FacetName.of("TableIndexName")

    fun createTestFixtureSchema(registrationApi: RegistrationApiDefaultImpl = RegistrationApiDefaultImpl()): Schema {

        registrationApi.configureSchema {
            newRootConcept(conceptName = databaseTableConceptName) {
                addTextFacet(facetName = tableNameFacetName) { _, value -> value.uppercase() }

                newChildConcept(conceptName = databaseTableFieldConceptName) {
                    addTextFacet(tableFieldNameFacetName) { _, value -> value.uppercase() }
                    addTextFacet(tableFieldTypeFacetName) // TODO use enumeration as soon as available
                    addIntegerNumberFacet(tableFieldLengthFacetName, setOf(tableFieldTypeFacetName))
                    addCalculatedTextFacet(
                        facetName = tableNameAndFieldNameFacetName,
                        dependingOnFacets = setOf(tableFieldNameFacetName)
                    ) { node -> "TODO write <TableName>.<FieldName>" } // TODO write simple code example as soon as nodes have properties

                    newChildConcept(conceptName = databaseTableFieldForeignKeyConceptName) {
                        addConceptReferenceFacet(tableFieldForeignKeyConceptIdFacetName, databaseTableConceptName)
                    }
                    newChildConcept(conceptName = databaseTableFieldIndexConceptName) {
                        addTextFacet(tableIndexNameFacetName) { _, value -> value.uppercase() }
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

                val targetFiles = templateNodesProvider
                    .targetGeneratedFileForEachTemplateNode(databaseTableConceptName) { templateNode ->
                        Paths.get("db_${templateNode.facetValues.asString(tableNameFacetName)}.create.sql")
                    }

                return@newTemplate TemplateRenderer(targetFiles) { targetGeneratedFileWithModel: TargetGeneratedFileWithModel ->
                    return@TemplateRenderer StringContentByteIterator(
                        """
                           -- content of ${targetGeneratedFileWithModel.targetFile}
                           CREATE TABLE ${targetGeneratedFileWithModel.model.asSingleTemplateNode.facetValues.asString(tableNameFacetName)} ;
                           --
                        """.trimIndent()
                    )
                }
            }

            // test a single node file generation
            newTemplate { templateNodesProvider ->

                val templateNodes = templateNodesProvider
                    .fetchTemplateNodes(databaseTableConceptName)

                return@newTemplate TemplateRenderer(setOf(TargetGeneratedFileWithModel(Paths.get("index.sql"), templateNodes))) { targetGeneratedFileWithModel: TargetGeneratedFileWithModel ->
                    return@TemplateRenderer StringContentByteIterator(
                        """
                           -- content of ${targetGeneratedFileWithModel.targetFile}
                           ${templateNodes.nodes.joinToString("\n") { "-- ${it.conceptIdentifier.code}: ${it.facetValues.asString(
                            tableNameFacetName)}" }}
                           --
                        """.trimIndent()
                    )
                }
            }

        }

        return registrationApi.provideTemplates()
    }

    fun createModelInputData():ModelInputData {
        val modelInputDataCollector = ModelInputDataCollector()

        val personTableId = ConceptIdentifier.of("Person")
        modelInputDataCollector.attachConceptData(
            conceptName = databaseTableConceptName,
            conceptIdentifier = personTableId,
            parentConceptIdentifier = null,
            facetValues = arrayOf(
                Pair(tableNameFacetName, TextFacetValue("Person"))
            )
        )

        return modelInputDataCollector.provideModelInputData()
    }
}
