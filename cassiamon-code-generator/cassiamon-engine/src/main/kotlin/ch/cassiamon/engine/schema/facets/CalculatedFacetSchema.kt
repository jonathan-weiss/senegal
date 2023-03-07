package ch.cassiamon.engine.schema.facets

import ch.cassiamon.pluginapi.CalculatedFacetDescriptor
import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.model.ConceptModelNode

class CalculatedFacetSchema<O>(
    conceptName: ConceptName,
    calculatedFacetDescriptor: CalculatedFacetDescriptor<O>,
    val facetCalculationFunction: (ConceptModelNode) -> O
): FacetSchema<O>(conceptName = conceptName, facetDescriptor = calculatedFacetDescriptor)
