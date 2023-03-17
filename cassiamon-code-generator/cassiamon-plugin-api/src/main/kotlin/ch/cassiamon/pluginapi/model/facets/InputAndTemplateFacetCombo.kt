package ch.cassiamon.pluginapi.model.facets

import ch.cassiamon.pluginapi.FacetName
import ch.cassiamon.pluginapi.model.ConceptModelNodeCalculationData

class InputAndTemplateFacetCombo<out I, out T>(
    override val facetName: FacetName,
    inputFacet: InputFacet<I>,
    templateFacet: TemplateFacet<T>,
    override val facetCalculationFunction: (ConceptModelNodeCalculationData) -> T,
): InputFacet<I> by inputFacet, TemplateFacet<T> by templateFacet, InputAndTemplateFacet<I, T>
