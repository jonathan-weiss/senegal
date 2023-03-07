package ch.cassiamon.pluginapi

import ch.cassiamon.pluginapi.model.ConceptModelNode


class CalculatedOptionalConceptReferenceFacetDescriptor private constructor(
    facetName: FacetName,
    isMandatoryFacetValue: Boolean,
    val referencedConceptName: ConceptName
): CalculatedFacetDescriptor<ConceptModelNode?>(
    facetName = facetName,
    isMandatoryFacetValue = isMandatoryFacetValue,
) {

    companion object {
        fun of(name: String, referencedConceptName: ConceptName): CalculatedOptionalConceptReferenceFacetDescriptor {
            return CalculatedOptionalConceptReferenceFacetDescriptor(
                facetName = FacetName.of(name),
                isMandatoryFacetValue = false,
                referencedConceptName = referencedConceptName,
            )
        }
    }
}
