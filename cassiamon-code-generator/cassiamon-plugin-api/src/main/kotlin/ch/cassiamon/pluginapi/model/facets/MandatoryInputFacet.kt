package ch.cassiamon.pluginapi.model.facets

import ch.cassiamon.pluginapi.FacetName

class MandatoryInputFacet<I>(override val facetName: FacetName,
                             override val isMandatoryInputFacetValue: Boolean = true) : InputFacet<I>
