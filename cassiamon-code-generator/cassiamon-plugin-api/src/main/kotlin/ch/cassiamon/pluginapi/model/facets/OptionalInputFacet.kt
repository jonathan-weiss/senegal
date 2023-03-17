package ch.cassiamon.pluginapi.model.facets

import ch.cassiamon.pluginapi.FacetName

class OptionalInputFacet<I>(override val facetName: FacetName,
                            override val isMandatoryInputFacetValue: Boolean = false) : InputFacet<I> {
}
