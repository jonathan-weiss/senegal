package ch.cassiamon.pluginapi.registration

import ch.cassiamon.pluginapi.registration.types.IntegerNumberFacetFunction

interface IntegerNumberFacetRegistration: FacetRegistration {

    fun addFacetFunction(facetFunction: IntegerNumberFacetFunction)

}
