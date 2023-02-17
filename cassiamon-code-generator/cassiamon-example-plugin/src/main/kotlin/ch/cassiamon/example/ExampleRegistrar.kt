package ch.cassiamon.example

import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName
import ch.cassiamon.pluginapi.ProjectName
import ch.cassiamon.pluginapi.registration.Registrar
import ch.cassiamon.pluginapi.registration.RegistrationApi

class ExampleRegistrar: Registrar(ProjectName.of("ExampleProject")) {

    override fun configure(registrationApi: RegistrationApi) {
        println("In the $projectName registrar")
        registrationApi.configure {
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
    }
}
