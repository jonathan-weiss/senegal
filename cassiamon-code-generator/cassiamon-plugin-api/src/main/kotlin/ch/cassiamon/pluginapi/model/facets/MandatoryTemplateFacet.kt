package ch.cassiamon.pluginapi.model.facets

import ch.cassiamon.pluginapi.FacetName

class MandatoryTemplateFacet<T>(override val facetName: FacetName,
                                override val isMandatoryTemplateFacetValue: Boolean = true) : TemplateFacet<T> {
}
