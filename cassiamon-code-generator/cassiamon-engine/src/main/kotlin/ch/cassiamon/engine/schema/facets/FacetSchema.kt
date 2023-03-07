package ch.cassiamon.engine.schema.facets

import ch.cassiamon.pluginapi.*

sealed class FacetSchema<O>(
    val conceptName: ConceptName,
    val facetDescriptor: FacetDescriptor<O>,
    ) {
    val facetType: FacetType
        get() = when(facetDescriptor) {
            is ManualMandatoryConceptReferenceFacetDescriptor -> FacetType.CONCEPT_REFERENCE
            is ManualMandatoryIntegerNumberFacetDescriptor -> FacetType.INTEGER_NUMBER
            is ManualMandatoryTextFacetDescriptor -> FacetType.TEXT
            is ManualOptionalConceptReferenceFacetDescriptor -> FacetType.CONCEPT_REFERENCE
            is ManualOptionalIntegerNumberFacetDescriptor -> FacetType.INTEGER_NUMBER
            is ManualOptionalTextFacetDescriptor -> FacetType.TEXT
            is CalculatedMandatoryConceptReferenceFacetDescriptor -> FacetType.CONCEPT_REFERENCE
            is CalculatedMandatoryIntegerNumberFacetDescriptor -> FacetType.INTEGER_NUMBER
            is CalculatedMandatoryTextFacetDescriptor -> FacetType.TEXT
            is CalculatedOptionalConceptReferenceFacetDescriptor -> FacetType.CONCEPT_REFERENCE
            is CalculatedOptionalIntegerNumberFacetDescriptor -> FacetType.INTEGER_NUMBER
            is CalculatedOptionalTextFacetDescriptor -> FacetType.TEXT
        }

}
