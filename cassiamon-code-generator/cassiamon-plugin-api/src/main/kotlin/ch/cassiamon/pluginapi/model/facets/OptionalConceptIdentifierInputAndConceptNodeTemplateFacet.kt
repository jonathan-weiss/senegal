package ch.cassiamon.pluginapi.model.facets

import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName
import ch.cassiamon.pluginapi.model.ConceptModelNodeCalculationData

class OptionalConceptIdentifierInputAndConceptNodeTemplateFacet private constructor(override val facetName: FacetName)
    : OptionalInputAndTemplateFacet<OptionalConceptReferenceFacetKotlinType, OptionalConceptFacetKotlinType> {

    override val inputFacetType: OptionalFacetType<OptionalConceptReferenceFacetKotlinType>
        get() = OptionalConceptReferenceFacetType

    override val templateFacetType: OptionalFacetType<OptionalConceptFacetKotlinType>
        get() = OptionalConceptFacetType

    override val facetCalculationFunction: (ConceptModelNodeCalculationData) -> OptionalConceptFacetKotlinType
        get() = { calculationData -> calculationData.inputFacetValues.facetValue(this)
            ?.let { calculationData.conceptModelNodePool.getConcept(it) } }

    companion object {
        fun of(facetName: String, referencedConceptName: ConceptName): OptionalConceptIdentifierInputAndConceptNodeTemplateFacet {
            // TODO what is with the target concept?
            return OptionalConceptIdentifierInputAndConceptNodeTemplateFacet(FacetName.of(facetName))
        }
    }
}
