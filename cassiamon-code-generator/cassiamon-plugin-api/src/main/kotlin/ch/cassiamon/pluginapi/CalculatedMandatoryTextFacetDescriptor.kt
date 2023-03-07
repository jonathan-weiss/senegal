package ch.cassiamon.pluginapi

import ch.cassiamon.pluginapi.model.ConceptModelNode


class CalculatedMandatoryTextFacetDescriptor private constructor(
    facetName: FacetName,
    isMandatoryFacetValue: Boolean,
): CalculatedFacetDescriptor<String>(
    facetName = facetName,
    isMandatoryFacetValue = isMandatoryFacetValue,
) {

    companion object {
        fun of(name: String): CalculatedMandatoryTextFacetDescriptor {
            return CalculatedMandatoryTextFacetDescriptor(
                facetName = FacetName.of(name),
                isMandatoryFacetValue = true,
            )
        }
    }
}
