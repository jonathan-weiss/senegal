package ch.cassiamon.engine

import ch.cassiamon.engine.inputsource.ModelInputData
import ch.cassiamon.engine.inputsource.ModelInputDataCollector
import ch.cassiamon.engine.schema.registration.RegistrationApiDefaultImpl
import ch.cassiamon.engine.schema.Schema
import ch.cassiamon.pluginapi.*
import ch.cassiamon.pluginapi.model.ConceptIdentifier
import ch.cassiamon.pluginapi.model.facets.MandatoryNumberInputAndTemplateFacet
import ch.cassiamon.pluginapi.model.facets.MandatoryTextInputAndTemplateFacet
import ch.cassiamon.pluginapi.model.facets.MandatoryTextTemplateFacet
import ch.cassiamon.pluginapi.model.facets.OptionalConceptIdentifierInputAndConceptNodeTemplateFacet
import ch.cassiamon.pluginapi.registration.TemplateFunction
import ch.cassiamon.pluginapi.template.TargetGeneratedFileWithModel
import ch.cassiamon.pluginapi.template.TemplateRenderer
import ch.cassiamon.pluginapi.template.helper.StringContentByteIterator
import java.nio.file.Paths

object TestFixtures {
    val databaseTableConceptName = ConceptName.of("DatabaseTable")
    val databaseTableFieldConceptName = ConceptName.of("DatabaseField")
    val databaseTableFieldIndexConceptName = ConceptName.of("DatabaseFieldIndex")
    val tableNameFacet = MandatoryTextInputAndTemplateFacet.of("TableName")
    val tableFieldNameFacet = MandatoryTextInputAndTemplateFacet.of("FieldName")
    val tableFieldTypeFacet = MandatoryTextInputAndTemplateFacet.of("FieldType")
    val tableFieldLengthFacet = MandatoryNumberInputAndTemplateFacet.of("FieldLength")
    val tableFieldForeignKeyConceptIdFacet = OptionalConceptIdentifierInputAndConceptNodeTemplateFacet.of("FieldForeignKey", databaseTableConceptName)
    val tableNameAndFieldNameFacet = MandatoryTextTemplateFacet.of("TableNameAndFieldName")
    val tableIndexNameFacet = MandatoryTextInputAndTemplateFacet.of("TableIndexName")

    fun createTestFixtureSchema(registrationApi: RegistrationApiDefaultImpl = RegistrationApiDefaultImpl()): Schema {

        registrationApi.configureSchema {
            newRootConcept(conceptName = databaseTableConceptName) {
                addFacet(tableNameFacet)

                newChildConcept(conceptName = databaseTableFieldConceptName) {
                    addFacet(tableFieldNameFacet)
                    addFacet(tableFieldTypeFacet) // TODO use enumeration as soon as available
                    addFacet(tableFieldLengthFacet)
                    addFacet(tableNameAndFieldNameFacet) { _ -> "TODO write <TableName>.<FieldName>" } // TODO write simple code example as soon as nodes have properties

                    addFacet(tableFieldForeignKeyConceptIdFacet)
                    newChildConcept(conceptName = databaseTableFieldIndexConceptName) {
                        addFacet(tableIndexNameFacet)
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
                    .map { templateNode -> TargetGeneratedFileWithModel(Paths.get("db_${templateNode.templateFacetValues.facetValue(tableNameFacet)}.create.sql"), templateNodes) }
                    .toSet()


                return@newTemplate TemplateRenderer(tagetGeneratedFiles) { targetGeneratedFileWithModel: TargetGeneratedFileWithModel ->
                    return@TemplateRenderer StringContentByteIterator(
                        """
                           -- content of ${targetGeneratedFileWithModel.targetFile}
                           CREATE TABLE ${targetGeneratedFileWithModel.model.first().templateFacetValues.facetValue(tableNameFacet)} ;
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
                           ${templateNodes.joinToString("\n") { "-- ${it.conceptIdentifier.code}: ${it.templateFacetValues.facetValue(
                            tableNameFacet)}" }}
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
        ).addFacetValue(tableNameFacet, "Person").attach()

        return modelInputDataCollector.provideModelInputData()
    }
}
