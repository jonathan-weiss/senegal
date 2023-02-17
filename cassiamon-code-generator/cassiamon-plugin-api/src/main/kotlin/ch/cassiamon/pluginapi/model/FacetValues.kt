package ch.cassiamon.pluginapi.model

import ch.cassiamon.pluginapi.FacetName

interface FacetValues {

    fun asString(facetName: FacetName): String

    fun asInt(facetName: FacetName): Int

    fun asConceptIdentifier(facetName: FacetName): ConceptIdentifier

}
