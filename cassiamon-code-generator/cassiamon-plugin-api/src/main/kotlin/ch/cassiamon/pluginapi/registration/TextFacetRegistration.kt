package ch.cassiamon.pluginapi.registration

import ch.cassiamon.pluginapi.registration.types.TextFacetFunction

interface TextFacetRegistration: FacetRegistration {

    fun addFacetFunction(facetFunction: TextFacetFunction)

}
