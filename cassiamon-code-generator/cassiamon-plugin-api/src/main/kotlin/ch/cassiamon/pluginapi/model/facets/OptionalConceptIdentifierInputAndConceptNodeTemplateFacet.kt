package ch.cassiamon.pluginapi.model.facets

import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName
import ch.cassiamon.pluginapi.model.ConceptModelNodeCalculationData

class OptionalConceptIdentifierInputAndConceptNodeTemplateFacet private constructor(override val facetName: FacetName)
    : OptionalInputAndTemplateFacet<ConceptReferenceFacetKotlinType?, ConceptFacetKotlinType?> {

    override val inputFacetType: OptionalFacetType<ConceptReferenceFacetKotlinType?>
        get() = OptionalConceptReferenceFacetType

    override val templateFacetType: OptionalFacetType<ConceptFacetKotlinType?>
        get() = OptionalConceptFacetType

    override val facetCalculationFunction: (ConceptModelNodeCalculationData) -> ConceptFacetKotlinType?
        get() = { calculationData -> calculationData.inputFacetValues.facetValue(this)
            ?.let { calculationData.conceptModelNodePool.getConcept(it) } }

    fun facetValue(value: ConceptReferenceFacetKotlinType?): InputFacetValue<ConceptReferenceFacetKotlinType?> {
        return InputFacetValue(this, value)
    }

    companion object {
        fun of(facetName: String, referencedConceptName: ConceptName): OptionalConceptIdentifierInputAndConceptNodeTemplateFacet {
            // TODO what is with the target concept?
            return OptionalConceptIdentifierInputAndConceptNodeTemplateFacet(FacetName.of(facetName))
        }
    }
}
