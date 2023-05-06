package ch.cassiamon.example

import ch.cassiamon.pluginapi.*
import ch.cassiamon.pluginapi.extensions.ClasspathLocation
import ch.cassiamon.pluginapi.model.ConceptIdentifier
import ch.cassiamon.pluginapi.model.facets.*
import ch.cassiamon.pluginapi.registration.DomainUnit
import ch.cassiamon.pluginapi.registration.RegistrationApi
import ch.cassiamon.pluginapi.template.helper.StringContentByteIterator
import ch.cassiamon.pluginapi.template.TargetGeneratedFileWithModel
import ch.cassiamon.pluginapi.template.TemplateRenderer
import ch.cassiamon.xml.schemagic.XmlSchemagicFactory
import java.nio.file.Path
import java.nio.file.Paths

class ExampleDomainUnit: DomainUnit(DomainUnitName.of("ExampleProject")) {
    companion object {
        val xmlDefinitionDirectory: Path = Paths.get("input-data")
        val outputDirectory: Path = Paths.get("output-data")
        val xmlFilename = "test-concept-input-data.xml"
    }

    private val testEntityConceptName = ConceptName.of("TestEntity")
    private val testEntityNameTextComposedFacet = MandatoryTextInputAndTemplateFacet.of("TestEntityName")
    private val testEntityAttributeConceptName = ConceptName.of("TestEntityAttribute")
    private val testEntityAttributeNameTextComposedFacet = MandatoryTextInputAndTemplateFacet.of("TestEntityAttributeName")
//    private val targetTestConceptName = ConceptName.of("TargetTestConcept")
//    private val testTextInputFacet = MandatoryTextInputFacet.of("TestRef")
//    private val testRefInputAndTemplateFacet = OptionalConceptIdentifierInputAndConceptNodeTemplateFacet.of("TestRefManual", targetTestConceptName)
//    private val testTextComposedFacet = MandatoryTextInputAndTemplateFacet.of("TestText")
//    private val testCalculatedIntTemplateFacet = OptionalNumberTemplateFacet.of("TestInt")
//    private val testCalculatedStringTemplateFacet = MandatoryTextTemplateFacet.of("TestCalcString")


    override fun configure(registrationApi: RegistrationApi) {
        registrationApi.configureSchema {
            newRootConcept(testEntityConceptName) {

                addFacet(testEntityNameTextComposedFacet)

                newChildConcept(testEntityAttributeConceptName) {
                    addFacet(testEntityAttributeNameTextComposedFacet)
                }
//                addFacet(testRefInputAndTemplateFacet)
//                addFacet(testCalculatedIntTemplateFacet) {
//                        it.conceptModelNode.templateFacetValues.facetValue(testRefInputAndTemplateFacet)?.conceptIdentifier.hashCode() // completely stupid
//                }
//                addFacet(testCalculatedStringTemplateFacet) {
//                    val testCalculatedIntValue = it.conceptModelNode.templateFacetValues.facetValue(testCalculatedIntTemplateFacet)
//                    return@addFacet "# is $testCalculatedIntValue"
//                }
//                addFacet(testTextComposedFacet)
            }
        }

        registrationApi.configureTemplates {
            // test a file generation for all nodes
            newTemplate { conceptModelGraph ->

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
            newTemplate { conceptModelGraph ->

                val templateNodes = conceptModelGraph.conceptModelNodesByConceptName(testEntityConceptName)
                val targetFilesWithModel = setOf(TargetGeneratedFileWithModel(outputDirectory.resolve("index.json"), templateNodes))

                return@newTemplate newTemplateRenderer(targetFilesWithModel) { targetGeneratedFileWithModel: TargetGeneratedFileWithModel ->
                    return@newTemplateRenderer StringContentByteIterator(
                        "content of ${targetGeneratedFileWithModel.targetFile} is ${targetGeneratedFileWithModel.model}"
                    )
                }

            }

            newTemplate { conceptModelGraph ->
                val templateNodes = conceptModelGraph.conceptModelNodesByConceptName(testEntityConceptName)
                val files = setOf(TargetGeneratedFileWithModel(outputDirectory.resolve("index.txt"), templateNodes))

                return@newTemplate newTemplateRendererWithClasspathTemplateExtension(
                    extensionName = ExampleExtensions.freemarkerTemplateExtensionName,
                    targetFilesWithModel = files,
                    templateClasspath = ClasspathLocation.of("/ch/cassiamon/example/templates/freemarker/example-template.ftl")
                )
            }
        }

        registrationApi.configureDataCollector {
            val dataCollector = receiveDataCollector()

            dataCollector
                .newConceptData(testEntityConceptName, ConceptIdentifier.of("MeinTestkonzept"))
                .addFacetValue(testEntityNameTextComposedFacet.facetValue( "MeinTestkonzept-Name"))
                .attach()

            dataCollector
                .newConceptData(testEntityConceptName, ConceptIdentifier.of("MeinZweitesTestkonzept"))
                .addFacetValue(testEntityNameTextComposedFacet.facetValue( "MeinZweitesTestkonzept-Name"))
                .attach()

            XmlSchemagicFactory.parseXml(receiveSchema(), dataCollector, xmlDefinitionDirectory, xmlFilename, receiveFileSystemAccess(), receiveLoggerFacade(), receiveParameterAccess())

        }
    }
}
