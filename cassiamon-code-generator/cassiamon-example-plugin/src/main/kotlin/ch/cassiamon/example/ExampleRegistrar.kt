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

class ExampleRegistrar: Registrar(ProjectName.of("ExampleProject")) {
    companion object {
        val xmlDefinitionDirectory: Path = Paths.get("definition/directory")
        val xmlFilename = "definition-file.xml"
    }

    private val testConceptName = ConceptName.of("TestConcept")
    private val targetTestConceptName = ConceptName.of("TargetTestConcept")
    private val testTextInputFacet = MandatoryTextInputFacet.of("TestRef")
    private val testRefInputAndTemplateFacet = OptionalConceptIdentifierInputAndConceptNodeTemplateFacet.of("TestRefManual", targetTestConceptName)
    private val testTextComposedFacet = MandatoryTextInputAndTemplateFacet.of("TestText")
    private val testCalculatedIntTemplateFacet = OptionalNumberTemplateFacet.of("TestInt")
    private val testCalculatedStringTemplateFacet = MandatoryTextTemplateFacet.of("TestCalcString")


    override fun configure(registrationApi: RegistrationApi) {


        println("In the $projectName registrar")
        registrationApi.configureSchema {
            newRootConcept(testConceptName) {

                addFacet(testTextInputFacet)
                addFacet(testRefInputAndTemplateFacet)
                addFacet(testCalculatedIntTemplateFacet) {
                        it.conceptModelNode.templateFacetValues.facetValue(testRefInputAndTemplateFacet)?.conceptIdentifier.hashCode() // completely stupid
                }
                addFacet(testCalculatedStringTemplateFacet) {
                    val testCalculatedIntValue = it.conceptModelNode.templateFacetValues.facetValue(testCalculatedIntTemplateFacet)
                    return@addFacet "# is $testCalculatedIntValue"
                }
                addFacet(testTextComposedFacet)
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
                    .conceptModelNodesByConceptName(ConceptName.of("TestConcept"))

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
                .newConceptData(testConceptName, ConceptIdentifier.of("MeinTestkonzept"))
                .addFacetValue(testTextInputFacet.facetValue( "UUID"))
                .attach()

            dataCollector
                .newConceptData(testConceptName, ConceptIdentifier.of("MeinZweitesTestkonzept"))
                .addFacetValue(testTextInputFacet.facetValue( "UUID"))
                .attach()

            XmlSchemagicFactory.parseXml(receiveSchema(), dataCollector, xmlDefinitionDirectory, xmlFilename, receiveFileSystemAccess(), receiveLoggerFacade(), receiveParameterAccess())

        }
    }
}
