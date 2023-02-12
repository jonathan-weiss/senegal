package ch.cassiamon.pluginapi.registration

import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName

interface ConceptRegistration {

    fun newTextFacet(facetName: FacetName, textFacetRegistration: TextFacetRegistration.() -> Unit)
    fun withExistingTextFacet(facetName: FacetName, textFacetRegistration: TextFacetRegistration.() -> Unit)

    fun newIntegerNumberFacet(facetName: FacetName, integerNumberFacetRegistration: IntegerNumberFacetRegistration.() -> Unit)
    fun withExistingIntegerNumberFacet(facetName: FacetName, integerNumberFacetRegistration: IntegerNumberFacetRegistration.() -> Unit)

    fun newConceptReferenceFacet(facetName: FacetName, referencedConcept: ConceptName, conceptReferenceFacetRegistration: ConceptReferenceFacetRegistration.() -> Unit)

    fun withExistingConceptReferenceFacet(facetName: FacetName, referencedConcept: ConceptName, conceptReferenceFacetRegistration: ConceptReferenceFacetRegistration.() -> Unit)
}
