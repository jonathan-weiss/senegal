package ch.cassiamon.engine.model.facets

import ch.cassiamon.pluginapi.*
import ch.cassiamon.pluginapi.model.ConceptIdentifier

interface ManualFacetValueAddition {

    fun addTextFacetValue(facet: ManualOptionalTextFacetDescriptor, value: String?)
    fun addTextFacetValue(facet: ManualMandatoryTextFacetDescriptor, value: String)
    fun addIntegerNumberFacetValue(facet: ManualOptionalIntegerNumberFacetDescriptor, value: Int?)
    fun addIntegerNumberFacetValue(facet: ManualMandatoryIntegerNumberFacetDescriptor, value: Int)
    fun addConceptReferenceFacetValue(facet: ManualOptionalConceptReferenceFacetDescriptor, value: ConceptIdentifier?)
    fun addConceptReferenceFacetValue(facet: ManualMandatoryConceptReferenceFacetDescriptor, value: ConceptIdentifier)
}
