package ch.cassiamon.pluginapi.model.facets

import ch.cassiamon.pluginapi.FacetName

class InputAndTemplateFacetCombo<out I, out T>(
    override val facetName: FacetName,
    inputFacet: InputFacet<I>,
    templateFacet: TemplateFacet<T>
): InputFacet<I> by inputFacet, TemplateFacet<T> by templateFacet, InputAndTemplateFacet<I, T>
