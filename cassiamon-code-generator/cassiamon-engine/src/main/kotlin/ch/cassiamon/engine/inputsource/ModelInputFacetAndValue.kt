package ch.cassiamon.engine.inputsource

import ch.cassiamon.engine.model.types.FacetValue
import ch.cassiamon.pluginapi.FacetName

data class ModelInputFacetAndValue(
    val facetName: FacetName,
    val facetValue: FacetValue,
)
