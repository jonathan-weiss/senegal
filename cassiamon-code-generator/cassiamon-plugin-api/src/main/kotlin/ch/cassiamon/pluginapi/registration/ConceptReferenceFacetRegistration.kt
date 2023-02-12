package ch.cassiamon.pluginapi.registration

import ch.cassiamon.pluginapi.registration.types.ConceptReferenceFacetFunction

interface ConceptReferenceFacetRegistration: FacetRegistration {

    fun addFacetFunction(facetFunction: ConceptReferenceFacetFunction)

}
