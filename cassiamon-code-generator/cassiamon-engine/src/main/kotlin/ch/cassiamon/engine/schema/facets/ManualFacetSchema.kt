package ch.cassiamon.engine.schema.facets

import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.ManualFacetDescriptor

class ManualFacetSchema<I, O>(
    conceptName: ConceptName,
    manualFacetDescriptor: ManualFacetDescriptor<I, O>,
): FacetSchema<O>(conceptName = conceptName, facetDescriptor = manualFacetDescriptor)
