package ch.cassiamon.pluginapi

import ch.cassiamon.pluginapi.model.ConceptModelNode


class CalculatedMandatoryIntegerNumberFacetDescriptor private constructor(
    facetName: FacetName,
    isMandatoryFacetValue: Boolean,
): CalculatedFacetDescriptor<Int>(
    facetName = facetName,
    isMandatoryFacetValue = isMandatoryFacetValue,
) {

    companion object {
        fun of(name: String): CalculatedMandatoryIntegerNumberFacetDescriptor {
            return CalculatedMandatoryIntegerNumberFacetDescriptor(
                facetName = FacetName.of(name),
                isMandatoryFacetValue = true,
            )
        }
    }
}
