package ch.cassiamon.pluginapi.model.facets

import ch.cassiamon.pluginapi.FacetName

class OptionalTemplateFacet<T>(override val facetName: FacetName,
                               override val isMandatoryTemplateFacetValue: Boolean = false) : TemplateFacet<T>
