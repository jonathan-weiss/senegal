package ch.cassiamon.api.schema

import ch.cassiamon.api.*
import ch.cassiamon.api.model.ConceptModelNodeCalculationData
import ch.cassiamon.api.model.facets.TemplateFacet

class TemplateFacetSchema<T>(
    val conceptName: ConceptName,
    val templateFacet: TemplateFacet<T>,
    val facetCalculationFunction: (ConceptModelNodeCalculationData) -> T,
    )
