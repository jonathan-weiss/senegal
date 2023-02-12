package ch.cassiamon.engine.schema.registration

import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName
import ch.cassiamon.pluginapi.registration.*
import ch.cassiamon.pluginapi.registration.types.ConceptReferenceFacetFunction
import ch.cassiamon.pluginapi.registration.types.FacetFunction
import ch.cassiamon.pluginapi.registration.types.IntegerNumberFacetFunction
import ch.cassiamon.pluginapi.registration.types.TextFacetFunction

class MutableFacet(val concept: MutableConcept,
                   val facetName: FacetName,
                   val facetType: FacetType,
                   val facetDependencies: MutableSet<FacetDependency> = mutableSetOf(),
                   val facetFunctions: MutableList<in FacetFunction<*>> = mutableListOf(),
                   val referencedFacetConcept: ConceptName? = null
    ):
    FacetRegistration,
    ConceptReferenceFacetRegistration,
    IntegerNumberFacetRegistration,
    TextFacetRegistration
{

    override fun addFacetDependency(targetConceptName: ConceptName, targetFacetName: FacetName) {
        println("FacetRegistrationDefaultImpl.addFacetDependency $targetConceptName:$targetFacetName")
        facetDependencies.add(
            FacetDependency(
                targetConceptName = targetConceptName,
                targetFacetName = targetFacetName)
        )
    }

    override fun addFacetFunction(facetFunction: ConceptReferenceFacetFunction) {
        println("ConceptReferenceFacetRegistrationDefaultImpl:addFacetFunction $facetFunction")
        facetFunctions.add(facetFunction)
    }

    override fun addFacetFunction(facetFunction: IntegerNumberFacetFunction) {
        println("IntegerNumberFacetRegistrationDefaultImpl:addFacetFunction $facetFunction")
        facetFunctions.add(facetFunction)
    }

    override fun addFacetFunction(facetFunction: TextFacetFunction) {
        println("TextFacetRegistrationDefaultImpl:addFacetFunction $facetFunction")
        facetFunctions.add(facetFunction)
    }
}
