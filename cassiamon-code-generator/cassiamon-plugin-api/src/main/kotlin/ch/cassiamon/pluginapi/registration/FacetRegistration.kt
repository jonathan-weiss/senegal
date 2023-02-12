package ch.cassiamon.pluginapi.registration

import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName

interface FacetRegistration {

    fun addFacetDependency(targetConceptName: ConceptName, targetFacetName: FacetName)

}
