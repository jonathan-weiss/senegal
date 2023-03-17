package ch.cassiamon.pluginapi.model.facets

import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName
import ch.cassiamon.pluginapi.model.ConceptIdentifier
import ch.cassiamon.pluginapi.model.ConceptModelNode

class OptionalConceptIdentifierInputAndConceptNodeTemplateFacet private constructor(
    override val facetName: FacetName,
    private val inputFacet: InputFacet<OptionalConceptReferenceFacetType> = OptionalInputFacet(facetName),
    private val templateFacet: TemplateFacet<OptionalConceptFacetType> = OptionalTemplateFacet(facetName),
    private val facetComposition: InputAndTemplateFacetCombo<OptionalConceptReferenceFacetType, OptionalConceptFacetType> = InputAndTemplateFacetCombo(facetName, inputFacet, templateFacet)
): InputAndTemplateFacet<OptionalConceptReferenceFacetType, OptionalConceptFacetType> by facetComposition {

    companion object {
        fun of(facetName: String, referencedConceptName: ConceptName): OptionalConceptIdentifierInputAndConceptNodeTemplateFacet {
            // TODO what is with the target concept?
            return OptionalConceptIdentifierInputAndConceptNodeTemplateFacet(FacetName.of(facetName))
        }
    }
}
