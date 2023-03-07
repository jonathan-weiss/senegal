package ch.cassiamon.pluginapi

import ch.cassiamon.pluginapi.model.ConceptIdentifier
import ch.cassiamon.pluginapi.model.ConceptModelNode


class CalculatedMandatoryConceptReferenceFacetDescriptor private constructor(
    facetName: FacetName,
    isMandatoryFacetValue: Boolean,
    val referencedConceptName: ConceptName
): CalculatedFacetDescriptor<ConceptModelNode>(
    facetName = facetName,
    isMandatoryFacetValue = isMandatoryFacetValue,
) {

    companion object {
        fun of(name: String, referencedConceptName: ConceptName): CalculatedMandatoryConceptReferenceFacetDescriptor {
            return CalculatedMandatoryConceptReferenceFacetDescriptor(
                facetName = FacetName.of(name),
                isMandatoryFacetValue = true,
                referencedConceptName = referencedConceptName,
            )
        }
    }
}
