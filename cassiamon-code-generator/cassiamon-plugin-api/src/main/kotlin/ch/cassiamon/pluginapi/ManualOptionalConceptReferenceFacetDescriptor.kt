package ch.cassiamon.pluginapi

import ch.cassiamon.pluginapi.model.ConceptIdentifier
import ch.cassiamon.pluginapi.model.ConceptModelNode


class ManualOptionalConceptReferenceFacetDescriptor private constructor(
    facetName: FacetName,
    isMandatoryFacetValue: Boolean,
    val referencedConceptName: ConceptName
): ManualFacetDescriptor<ConceptIdentifier?, ConceptModelNode?>(facetName, isMandatoryFacetValue) {

    companion object {
        fun of(name: String, referencedConceptName: ConceptName): ManualOptionalConceptReferenceFacetDescriptor {
            return ManualOptionalConceptReferenceFacetDescriptor(
                facetName = FacetName.of(name),
                isMandatoryFacetValue = false,
                referencedConceptName = referencedConceptName,
            )
        }
    }
}
