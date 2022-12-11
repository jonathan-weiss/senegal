package ch.senegal.plugin.model

import ch.senegal.plugin.Concept
import ch.senegal.plugin.FacetName
import ch.senegal.plugin.PurposeName

interface ModelNode {
    fun concept(): Concept
    fun parentModelNode(): ModelNode?
    fun getFacetValue(purposeName: PurposeName, facetName: FacetName): FacetValue?
}
