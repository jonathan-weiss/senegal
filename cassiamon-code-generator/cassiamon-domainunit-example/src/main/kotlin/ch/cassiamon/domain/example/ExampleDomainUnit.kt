package ch.cassiamon.domain.example

import ch.cassiamon.api.*
import ch.cassiamon.api.extensions.ClasspathLocation
import ch.cassiamon.api.model.ConceptIdentifier
import ch.cassiamon.api.model.facets.*
import ch.cassiamon.api.registration.*
import ch.cassiamon.api.template.helper.StringContentByteIterator
import ch.cassiamon.api.template.TargetGeneratedFileWithModel
import ch.cassiamon.api.template.TemplateRenderer
import java.nio.file.Path
import java.nio.file.Paths

class ExampleDomainUnit: DomainUnit {
    companion object {
        val xmlDefinitionDirectory: Path = Paths.get("input-data")
        val outputDirectory: Path = Paths.get("output-data")
        val xmlFilename = "test-concept-input-data.xml"
    }

    private object TestEntityConcept {
        val conceptName = ConceptName.of("TestEntity")

        val nameComposedFacet = MandatoryTextInputAndTemplateFacet.of("TestEntityName")
        val numberComposedFacet = MandatoryNumberInputAndTemplateFacet.of("TestEntityNumberName")
        val nameTextOutputFacet = MandatoryTextTemplateFacet.of("TestEntityName") {
            return@of it.inputFacetValues.facetValue(nameComposedFacet)
        }
    }

    private object TestEntityAttributeConcept {
        val conceptName = ConceptName.of("TestEntityAttribute")
        val nameFacet = MandatoryTextInputAndTemplateFacet.of("TestEntityAttributeName")
    }


    //    private val targetTestConceptName = ConceptName.of("TargetTestConcept")
//    private val testTextInputFacet = MandatoryTextInputFacet.of("TestRef")
//    private val testRefInputAndTemplateFacet = OptionalConceptIdentifierInputAndConceptNodeTemplateFacet.of("TestRefManual", targetTestConceptName)
//    private val testTextComposedFacet = MandatoryTextInputAndTemplateFacet.of("TestText")
//    private val testCalculatedIntTemplateFacet = OptionalNumberTemplateFacet.of("TestInt")
//    private val testCalculatedStringTemplateFacet = MandatoryTextTemplateFacet.of("TestCalcString")
    override val domainUnitName: DomainUnitName
        get() = DomainUnitName.of("ExampleProject")

    override fun configureSchema(registration: SchemaRegistrationApi) {
        registration {
            newRootConcept(TestEntityConcept.conceptName) {

                addFacet(TestEntityConcept.nameComposedFacet)
                addFacet(TestEntityConcept.nameTextOutputFacet)

                newChildConcept(TestEntityAttributeConcept.conceptName) {
                    addFacet(TestEntityAttributeConcept.nameFacet)
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
    }


    override fun configureDataCollector(registration: InputSourceRegistrationApi) {
        registration {
            val dataCollector = receiveDataCollector()

            dataCollector
                .newConceptData(TestEntityConcept.conceptName, ConceptIdentifier.of("MeinTestkonzept"))
                .addFacetValue(TestEntityConcept.nameComposedFacet.facetValue( "MeinTestkonzept-Name"))
                .attach()

            dataCollector
                .newConceptData(TestEntityConcept.conceptName, ConceptIdentifier.of("MeinZweitesTestkonzept"))
                .addFacetValue(TestEntityConcept.nameComposedFacet.facetValue( "MeinZweitesTestkonzept-Name"))
                .attach()

            val inputFiles = setOf<Path>(xmlDefinitionDirectory.resolve(xmlFilename))
            dataCollectionWithFilesInputSourceExtension(
                extensionName = ExampleExtensions.freemarkerTemplateExtensionName,
                inputFiles = inputFiles,
            )
        }

    }

    override fun configureTemplates(registration: TemplatesRegistrationApi) {
        registration {
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
                val templateNodes = conceptModelGraph.conceptModelNodesByConceptName(TestEntityConcept.conceptName)
                val targetFilesWithModel = setOf(TargetGeneratedFileWithModel(outputDirectory.resolve("index.json"), templateNodes))

                return@newTemplate newTemplateRenderer(targetFilesWithModel) { targetGeneratedFileWithModel: TargetGeneratedFileWithModel ->
                    return@newTemplateRenderer StringContentByteIterator(
                        "content of ${targetGeneratedFileWithModel.targetFile} is ${targetGeneratedFileWithModel.model}"
                    )
                }

            }

            newTemplate { conceptModelGraph ->
                val templateNodes = conceptModelGraph.conceptModelNodesByConceptName(TestEntityConcept.conceptName)
                val files = setOf(TargetGeneratedFileWithModel(outputDirectory.resolve("index.txt"), templateNodes))

                return@newTemplate newTemplateRendererWithClasspathTemplateExtension(
                    extensionName = ExampleExtensions.freemarkerTemplateExtensionName,
                    targetFilesWithModel = files,
                    templateClasspath = ClasspathLocation.of("/ch/cassiamon/domain/example/templates/freemarker/example-template.ftl")
                )
            }
        }
    }
}
