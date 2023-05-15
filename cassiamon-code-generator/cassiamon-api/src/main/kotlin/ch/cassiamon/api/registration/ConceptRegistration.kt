package ch.cassiamon.api.registration

import ch.cassiamon.api.*
import ch.cassiamon.api.model.ConceptModelNodeCalculationData
import ch.cassiamon.api.model.facets.InputAndTemplateFacet
import ch.cassiamon.api.model.facets.InputFacet
import ch.cassiamon.api.model.facets.TemplateFacet

interface ConceptRegistration {
    fun newChildConcept(conceptName: ConceptName, conceptRegistration: (ConceptRegistration.() -> Unit))

    fun <I> addFacet(facet: InputFacet<I>)
    fun <O> addFacet(facet: TemplateFacet<O>)
    fun <I, O> addFacet(facet: InputAndTemplateFacet<I, O>)

}
