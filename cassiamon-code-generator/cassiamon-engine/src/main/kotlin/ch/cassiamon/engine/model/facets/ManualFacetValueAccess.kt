package ch.cassiamon.engine.model.facets

import ch.cassiamon.pluginapi.*
import ch.cassiamon.pluginapi.model.ConceptIdentifier

interface ManualFacetValueAccess {
    fun getIntegerNumberFacetValue(facet: ManualMandatoryIntegerNumberFacetDescriptor): Int
    fun getIntegerNumberFacetValue(facet: ManualOptionalIntegerNumberFacetDescriptor): Int?
    fun getTextFacetValue(facet: ManualMandatoryTextFacetDescriptor): String
    fun getTextFacetValue(facet: ManualOptionalTextFacetDescriptor): String?
    fun getConceptReferenceFacetValue(facet: ManualMandatoryConceptReferenceFacetDescriptor): ConceptIdentifier
    fun getConceptReferenceFacetValue(facet: ManualOptionalConceptReferenceFacetDescriptor): ConceptIdentifier?
    fun getFacetNames(): Set<FacetName>
}
