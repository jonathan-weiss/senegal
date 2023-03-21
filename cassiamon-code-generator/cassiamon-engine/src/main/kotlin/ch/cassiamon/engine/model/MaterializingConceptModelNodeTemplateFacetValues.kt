package ch.cassiamon.engine.model

import ch.cassiamon.pluginapi.FacetName
import ch.cassiamon.pluginapi.model.ConceptModelNode
import ch.cassiamon.pluginapi.model.ConceptModelNodeTemplateFacetValues
import ch.cassiamon.pluginapi.model.facets.*

class MaterializingConceptModelNodeTemplateFacetValues(
    private val conceptModelNode: ConceptModelNode
): ConceptModelNodeTemplateFacetValues {

    private var isFacetNamesMaterialized = false
    private var materializedAllTemplateFacetNames: Set<FacetName> = emptySet()

    private val materializedTemplateFacetValues: MutableMap<TemplateFacet<*>, TemplateFacetValue<*>> = mutableMapOf()
    private val materializedTemplateFacetNames: MutableSet<FacetName> = mutableSetOf() // separate set to support optional values with null

    data class TemplateFacetValue<T>(
        val templateFacet: TemplateFacet<T>,
        val templateFacetValue: T,
    )

    override fun allTemplateFacetNames(): Set<FacetName> {
        materializeFacetNamesIfNecessary()
        return materializedAllTemplateFacetNames
    }

    override fun <T> facetValue(templateFacet: TemplateFacet<T>): T {
        materializeFacetIfNecessary(templateFacet)
        // TODO add a facet value collector

        return when(templateFacet) {
            is MandatoryConceptIdentifierInputAndConceptNodeTemplateFacet -> facetValueInternal(templateFacet)
            is MandatoryNumberInputAndTemplateFacet -> facetValueInternal(templateFacet)
            is MandatoryTextInputAndTemplateFacet -> facetValueInternal(templateFacet)
            is OptionalConceptIdentifierInputAndConceptNodeTemplateFacet -> facetValueInternal(templateFacet)
            is MandatoryTextTemplateFacet -> facetValueInternal(templateFacet)
            is OptionalNumberTemplateFacet -> facetValueInternal(templateFacet)
        }
    }

    private inline fun <reified T> facetValueInternal(facet: TemplateFacet<T>): T {
        val facetValueWrapper: TemplateFacetValue<*>? = materializedTemplateFacetValues[facet]

        val facetValue = facetValueWrapper?.templateFacetValue ?: return null as T

        if(facetValue is T) {
            return facetValue
        }
        throw IllegalArgumentException("Facet value for facet type '$facet' has wrong type: $facetValue")

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
        if(!materializedTemplateFacetNames.contains(facet.facetName)) {
            materializedTemplateFacetValues[facet] = TemplateFacetValue(facet, conceptModelNode.templateFacetValues.facetValue(facet))
            materializedTemplateFacetNames.add(facet.facetName)
        }
    }
}