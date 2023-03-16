package ch.cassiamon.engine

import ch.cassiamon.engine.inputsource.ModelInputData
import ch.cassiamon.engine.inputsource.ModelInputDataCollector
import ch.cassiamon.engine.schema.registration.RegistrationApiDefaultImpl
import ch.cassiamon.engine.schema.Schema
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
    val tableNameFacetDescriptor = ManualMandatoryTextFacetDescriptor.of("TableName")
    val tableFieldNameFacetDescriptor = ManualMandatoryTextFacetDescriptor.of("FieldName")
    val tableFieldTypeFacetDescriptor = ManualMandatoryTextFacetDescriptor.of("FieldType")
    val tableFieldLengthFacetDescriptor = ManualMandatoryIntegerNumberFacetDescriptor.of("FieldLength")
    val tableFieldForeignKeyConceptIdFacetDescriptor = ManualOptionalConceptReferenceFacetDescriptor.of("FieldForeignKey", databaseTableConceptName)
    val tableNameAndFieldNameFacetDescriptor = CalculatedMandatoryTextFacetDescriptor.of("TableNameAndFieldName")
    val tableIndexNameFacetDescriptor = ManualMandatoryTextFacetDescriptor.of("TableIndexName")

    fun createTestFixtureSchema(registrationApi: RegistrationApiDefaultImpl = RegistrationApiDefaultImpl()): Schema {

        registrationApi.configureSchema {
            newRootConcept(conceptName = databaseTableConceptName) {
                addFacet(facetDescriptor = tableNameFacetDescriptor)

                newChildConcept(conceptName = databaseTableFieldConceptName) {
                    addFacet(facetDescriptor = tableFieldNameFacetDescriptor)
                    addFacet(facetDescriptor = tableFieldTypeFacetDescriptor) // TODO use enumeration as soon as available
                    addFacet(facetDescriptor = tableFieldLengthFacetDescriptor)
                    addFacet(facetDescriptor = tableNameAndFieldNameFacetDescriptor) { node -> "TODO write <TableName>.<FieldName>" } // TODO write simple code example as soon as nodes have properties

                    addFacet(tableFieldForeignKeyConceptIdFacetDescriptor)
                    newChildConcept(conceptName = databaseTableFieldIndexConceptName) {
                        addFacet(facetDescriptor = tableIndexNameFacetDescriptor)
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
                    .map { templateNode -> TargetGeneratedFileWithModel(Paths.get("db_${templateNode.facetValues.facetValue(tableNameFacetDescriptor)}.create.sql"), templateNodes) }
                    .toSet()


                return@newTemplate TemplateRenderer(tagetGeneratedFiles) { targetGeneratedFileWithModel: TargetGeneratedFileWithModel ->
                    return@TemplateRenderer StringContentByteIterator(
                        """
                           -- content of ${targetGeneratedFileWithModel.targetFile}
                           CREATE TABLE ${targetGeneratedFileWithModel.model.first().facetValues.facetValue(tableNameFacetDescriptor)} ;
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
                           ${templateNodes.joinToString("\n") { "-- ${it.conceptIdentifier.code}: ${it.facetValues.facetValue(
                            tableNameFacetDescriptor)}" }}
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
        ).addFacetValue(tableNameFacetDescriptor, "Person").attach()

        return modelInputDataCollector.provideModelInputData()
    }
}
