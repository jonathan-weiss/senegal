package ch.cassiamon.pluginapi.registration

import ch.cassiamon.pluginapi.*
import ch.cassiamon.pluginapi.model.ConceptModelNode
import ch.cassiamon.pluginapi.model.TemplateFacetCalculationData
import ch.cassiamon.pluginapi.model.facets.InputAndTemplateFacet
import ch.cassiamon.pluginapi.model.facets.InputFacet
import ch.cassiamon.pluginapi.model.facets.TemplateFacet

interface ConceptRegistration {
    fun newChildConcept(conceptName: ConceptName, conceptRegistration: (ConceptRegistration.() -> Unit))

    fun <I> addFacet(facet: InputFacet<I>)
    fun <O> addFacet(facet: TemplateFacet<O>,
                     facetCalculationFunction: (TemplateFacetCalculationData) -> O)
    fun <I, O> addFacet(facet: InputAndTemplateFacet<I, O>)

}
