package ch.cassiamon.engine.schema.types

import ch.cassiamon.pluginapi.*

sealed class Facet (
    val conceptName: ConceptName,
    val facetName: FacetName,
) {

    val facetType: FacetType
        get() = when(facetName) {
            is NameOfMandatoryConceptReferenceFacet -> FacetType.CONCEPT_REFERENCE
            is NameOfMandatoryIntegerNumberFacet -> FacetType.INTEGER_NUMBER
            is NameOfMandatoryTextFacet -> FacetType.TEXT
            is NameOfOptionalConceptReferenceFacet -> FacetType.CONCEPT_REFERENCE
            is NameOfOptionalIntegerNumberFacet -> FacetType.INTEGER_NUMBER
            is NameOfOptionalTextFacet -> FacetType.TEXT
        }
    abstract val isCalculatedFacet: Boolean
    abstract val isManualFacet: Boolean
}
