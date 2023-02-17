package ch.cassiamon.pluginapi.model

import ch.cassiamon.pluginapi.ConceptName

interface ConceptNode {
    val conceptName: ConceptName
    val conceptIdentifier: ConceptIdentifier
    val facetValues: FacetValues

}
