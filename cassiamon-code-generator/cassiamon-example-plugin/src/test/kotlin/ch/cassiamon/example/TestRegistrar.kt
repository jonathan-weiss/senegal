package ch.cassiamon.example

import ch.cassiamon.pluginapi.*
import ch.cassiamon.pluginapi.model.ConceptIdentifier
import ch.cassiamon.pluginapi.model.facets.*
import ch.cassiamon.pluginapi.registration.Registrar
import ch.cassiamon.pluginapi.registration.RegistrationApi
import ch.cassiamon.pluginapi.template.helper.StringContentByteIterator
import ch.cassiamon.pluginapi.template.TargetGeneratedFileWithModel
import ch.cassiamon.pluginapi.template.TemplateRenderer
import ch.cassiamon.xml.schemagic.XmlSchemagicFactory
import java.nio.file.Path
import java.nio.file.Paths

class TestRegistrar: Registrar(ProjectName.of("TestProject")) {
    companion object {
        val xmlDefinitionDirectory: Path = Paths.get("definition/directory")
        val xmlFilename = "definition-file.xml"
    }

    private val testEntityConceptName = ConceptName.of("TestEntity")
    private val testEntityAttributeConceptName = ConceptName.of("TestEntityAttribute")
    private val testEntityNameInputFacet = MandatoryTextInputFacet.of("TestEntityName")
    private val testEntityAttributeNameInputFacet = MandatoryTextInputFacet.of("TestEntityAttributeName")


    override fun configure(registrationApi: RegistrationApi) {
        registrationApi.configureSchema {
            newRootConcept(testEntityConceptName) {

                addFacet(testEntityNameInputFacet)

                newChildConcept(testEntityAttributeConceptName) {
                    addFacet(testEntityAttributeNameInputFacet)
                }
            }
        }

        registrationApi.configureTemplates {
            // test a file generation for all nodes
            newTemplate { templateNodesProvider ->

//                val targetFiles = templateNodesProvider
//                    .targetGeneratedFileForEachTemplateNode(ConceptName.of("TestConcept")) { templateNode ->
//                    Paths.get("$templateNode.conceptIdentifier.code-xyz.json")
//                }

                val targetFiles = emptySet<TargetGeneratedFileWithModel>()

                return@newTemplate TemplateRenderer(targetFiles) { targetGeneratedFileWithModel: TargetGeneratedFileWithModel ->
                    return@TemplateRenderer StringContentByteIterator(
                        "content of ${targetGeneratedFileWithModel.targetFile} is ${targetGeneratedFileWithModel.model}"
                    )
                }
            }

            // test a single node file generation
            newTemplate { templateNodesProvider ->

                val templateNodes = templateNodesProvider
                    .conceptModelNodesByConceptName(testEntityConceptName)

                return@newTemplate TemplateRenderer(setOf(TargetGeneratedFileWithModel(Paths.get("index.json"), templateNodes))) { targetGeneratedFileWithModel: TargetGeneratedFileWithModel ->
                    return@TemplateRenderer StringContentByteIterator(
                        "content of ${targetGeneratedFileWithModel.targetFile} is ${targetGeneratedFileWithModel.model}"
                    )
                }
            }

        }

        registrationApi.configureDataCollector {
            val dataCollector = receiveDataCollector()

            dataCollector
                .newConceptData(testEntityConceptName, ConceptIdentifier.of("MeinTestkonzept"))
                .addFacetValue(testEntityNameInputFacet.facetValue( "MeinTestkonzeptName"))
                .attach()

            dataCollector
                .newConceptData(testEntityConceptName, ConceptIdentifier.of("MeinZweitesTestkonzept"))
                .addFacetValue(testEntityNameInputFacet.facetValue( "MeinZweitesTestkonzeptName"))
                .attach()

            XmlSchemagicFactory.parseXml(receiveSchema(), dataCollector, xmlDefinitionDirectory, xmlFilename, receiveFileSystemAccess(), receiveLoggerFacade(), receiveParameterAccess())

        }
    }
}
