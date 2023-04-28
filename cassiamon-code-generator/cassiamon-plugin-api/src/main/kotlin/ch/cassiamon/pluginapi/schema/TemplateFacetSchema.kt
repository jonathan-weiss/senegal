package ch.cassiamon.pluginapi.schema

import ch.cassiamon.pluginapi.*
import ch.cassiamon.pluginapi.model.ConceptModelNodeCalculationData
import ch.cassiamon.pluginapi.model.facets.TemplateFacet

class TemplateFacetSchema<T>(
    val conceptName: ConceptName,
    val templateFacet: TemplateFacet<T>,
    val facetCalculationFunction: (ConceptModelNodeCalculationData) -> T,
    )
