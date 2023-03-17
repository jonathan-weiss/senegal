package ch.cassiamon.engine.model

import ch.cassiamon.pluginapi.FacetName
import ch.cassiamon.pluginapi.model.ConceptModelNode
import ch.cassiamon.pluginapi.model.ConceptModelNodeTemplateFacetValues
import ch.cassiamon.pluginapi.model.facets.TemplateFacet

class MaterializingConceptModelNodeTemplateFacetValues(
    private val conceptModelNode: ConceptModelNode
): ConceptModelNodeTemplateFacetValues {

    private var isFacetNamesMaterialized = false
    private var materializedAllTemplateFacetNames: Set<FacetName> = emptySet()

    private val materializedTemplateFacetValues: MutableMap<FacetName, Any?> = mutableMapOf()
    private val materializedTemplateFacets: MutableSet<FacetName> = mutableSetOf() // separate set to support optional values with null

    override fun allTemplateFacetNames(): Set<FacetName> {
        materializeFacetNamesIfNecessary()
        return materializedAllTemplateFacetNames
    }

    override fun <T> facetValue(templateFacet: TemplateFacet<T>): T {
        materializeFacetIfNecessary(templateFacet)
        // TODO add a facet value collector
        return materializedTemplateFacetValues[templateFacet.facetName] as T
    }


    override fun get(key: String): Any? {
        TODO("Not yet implemented")
    }


    private fun materializeFacetNamesIfNecessary() {
        if(!isFacetNamesMaterialized) {
            materializedAllTemplateFacetNames = conceptModelNode.templateFacetValues.allTemplateFacetNames()
            isFacetNamesMaterialized = true
        }
    }

    private fun <T> materializeFacetIfNecessary(facet: TemplateFacet<T>) {
        if(!materializedTemplateFacets.contains(facet.facetName)) {
            materializedTemplateFacetValues[facet.facetName] = conceptModelNode.templateFacetValues.facetValue(facet)
            materializedTemplateFacets.add(facet.facetName)
        }
    }
}
