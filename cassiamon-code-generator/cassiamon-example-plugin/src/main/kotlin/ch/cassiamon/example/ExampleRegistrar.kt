package ch.cassiamon.example

import ch.cassiamon.pluginapi.*
import ch.cassiamon.pluginapi.registration.Registrar
import ch.cassiamon.pluginapi.registration.RegistrationApi
import ch.cassiamon.pluginapi.template.helper.StringContentByteIterator
import ch.cassiamon.pluginapi.template.TargetGeneratedFileWithModel
import ch.cassiamon.pluginapi.template.TemplateRenderer
import java.nio.file.Path
import java.nio.file.Paths

class ExampleRegistrar: Registrar(ProjectName.of("ExampleProject")) {

    val testConceptName = ConceptName.of("TestConcept")
    val targetTestConceptName = ConceptName.of("TargetTestConcept")

    val testRefFacet = ManualMandatoryConceptReferenceFacetDescriptor.of("TestRef", targetTestConceptName)
    val testCalculatedIntFacet = CalculatedMandatoryIntegerNumberFacetDescriptor.of("TestInt")
    val testCalculatedStringFacet = CalculatedMandatoryTextFacetDescriptor.of("TestCalcString")



    override fun configure(registrationApi: RegistrationApi) {
        println("In the $projectName registrar")
        registrationApi.configureSchema {
            newRootConcept(testConceptName) {
                // and more stuff
                addFacet(testRefFacet)

                addFacet(testCalculatedIntFacet) {
                    conceptModelNode -> conceptModelNode.facetValues
                        .asReferencedConceptModelNode(testRefFacet)
                        .conceptIdentifier.hashCode() // completely stupid
                }

                addFacet(testCalculatedStringFacet) { conceptModelNode ->
                    val testCalculatedIntValue = conceptModelNode.facetValues.asInt(testCalculatedIntFacet).toString()
                    return@addFacet "# is $testCalculatedIntValue"
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
                    .conceptModelNodesByConceptName(ConceptName.of("TestConcept"))

                return@newTemplate TemplateRenderer(setOf(TargetGeneratedFileWithModel(Paths.get("index.json"), templateNodes))) { targetGeneratedFileWithModel: TargetGeneratedFileWithModel ->
                    return@TemplateRenderer StringContentByteIterator(
                        "content of ${targetGeneratedFileWithModel.targetFile} is ${targetGeneratedFileWithModel.model}"
                    )
                }
            }

        }
    }
}
