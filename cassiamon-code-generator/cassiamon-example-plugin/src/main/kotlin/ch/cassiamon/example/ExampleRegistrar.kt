package ch.cassiamon.example

import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName
import ch.cassiamon.pluginapi.ProjectName
import ch.cassiamon.pluginapi.registration.Registrar
import ch.cassiamon.pluginapi.registration.RegistrationApi
import ch.cassiamon.pluginapi.template.helper.StringContentByteIterator
import ch.cassiamon.pluginapi.template.TargetGeneratedFileWithModel
import ch.cassiamon.pluginapi.template.TemplateRenderer
import java.nio.file.Paths

class ExampleRegistrar: Registrar(ProjectName.of("ExampleProject")) {

    override fun configure(registrationApi: RegistrationApi) {
        println("In the $projectName registrar")
        registrationApi.configureSchema {
            newRootConcept(ConceptName.of("TestConcept")) {
                // and more stuff
                addConceptReferenceFacet(
                    FacetName.of("TestRef"),
                    ConceptName.of("TargetTestConcept"))

                addIntegerNumberFacet(
                    FacetName.of("TestInt"),
                    setOf(FacetName.of("TestRef"))) {
                        conceptNode, value -> value + 42 // completely stupid
                }

                addCalculatedTextFacet(FacetName.of("TestCalcString"),
                    setOf(FacetName.of("TestInt"))) { conceptNode ->
                        val testIntValue = conceptNode.facetValues.asInt(FacetName.of("TestInt"))
                        return@addCalculatedTextFacet "# is $testIntValue"
                }
            }
        }

        registrationApi.configureTemplates {
            // test a file generation for all nodes
            newTemplate { templateNodesProvider ->

                val targetFiles = templateNodesProvider
                    .targetGeneratedFileForEachTemplateNode(ConceptName.of("TestConcept")) { templateNode ->
                    Paths.get("$templateNode.conceptIdentifier.code-xyz.json")
                }

                return@newTemplate TemplateRenderer(targetFiles) { targetGeneratedFileWithModel: TargetGeneratedFileWithModel ->
                    return@TemplateRenderer StringContentByteIterator(
                        "content of ${targetGeneratedFileWithModel.targetFile} is ${targetGeneratedFileWithModel.model}"
                    )
                }
            }

            // test a single node file generation
            newTemplate { templateNodesProvider ->

                val templateNodes = templateNodesProvider
                    .fetchTemplateNodes(ConceptName.of("TestConcept"))

                return@newTemplate TemplateRenderer(setOf(TargetGeneratedFileWithModel(Paths.get("index.json"), templateNodes))) { targetGeneratedFileWithModel: TargetGeneratedFileWithModel ->
                    return@TemplateRenderer StringContentByteIterator(
                        "content of ${targetGeneratedFileWithModel.targetFile} is ${targetGeneratedFileWithModel.model}"
                    )
                }
            }

        }
    }
}
