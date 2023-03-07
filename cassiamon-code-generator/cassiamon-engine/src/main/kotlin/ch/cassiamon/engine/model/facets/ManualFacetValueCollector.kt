package ch.cassiamon.engine.model.facets

import ch.cassiamon.pluginapi.*
import ch.cassiamon.pluginapi.model.ConceptIdentifier
import ch.cassiamon.pluginapi.model.ConceptModelNode

class ManualFacetValueCollector: ManualFacetValueAddition, ManualFacetValueAccess {

    private val optionalTextFacets: MutableMap<ManualOptionalTextFacetDescriptor, ManualFacetValue<String?, String?>> = mutableMapOf()
    private val mandatoryTextFacets: MutableMap<ManualMandatoryTextFacetDescriptor, ManualFacetValue<String, String>> = mutableMapOf()
    private val optionalNumberFacets: MutableMap<ManualOptionalIntegerNumberFacetDescriptor, ManualFacetValue<Int?, Int?>> = mutableMapOf()
    private val mandatoryNumberFacets: MutableMap<ManualMandatoryIntegerNumberFacetDescriptor, ManualFacetValue<Int, Int>> = mutableMapOf()
    private val optionalConceptReferenceFacets: MutableMap<ManualOptionalConceptReferenceFacetDescriptor, ManualFacetValue<ConceptIdentifier?, ConceptModelNode?>> = mutableMapOf()
    private val mandatoryConceptReferenceFacets: MutableMap<ManualMandatoryConceptReferenceFacetDescriptor, ManualFacetValue<ConceptIdentifier, ConceptModelNode>> = mutableMapOf()

    private val facetNames: MutableSet<FacetName> = mutableSetOf()

    override fun addTextFacetValue(facet: ManualOptionalTextFacetDescriptor, value: String?){
        optionalTextFacets[facet] = ManualFacetValue(facet, value)
        facetNames.add(facet.facetName)
    }

    override fun addTextFacetValue(facet: ManualMandatoryTextFacetDescriptor, value: String){
        mandatoryTextFacets[facet] = ManualFacetValue(facet, value)
        facetNames.add(facet.facetName)
    }

    override fun addIntegerNumberFacetValue(facet: ManualOptionalIntegerNumberFacetDescriptor, value: Int?){
        optionalNumberFacets[facet] = ManualFacetValue(facet, value)
        facetNames.add(facet.facetName)
    }

    override fun addIntegerNumberFacetValue(facet: ManualMandatoryIntegerNumberFacetDescriptor, value: Int){
        mandatoryNumberFacets[facet] = ManualFacetValue(facet, value)
        facetNames.add(facet.facetName)
    }

    override fun addConceptReferenceFacetValue(facet: ManualOptionalConceptReferenceFacetDescriptor, value: ConceptIdentifier?){
        optionalConceptReferenceFacets[facet] = ManualFacetValue(facet, value)
        facetNames.add(facet.facetName)
    }

    override fun addConceptReferenceFacetValue(facet: ManualMandatoryConceptReferenceFacetDescriptor, value: ConceptIdentifier){
        mandatoryConceptReferenceFacets[facet] = ManualFacetValue(facet, value)
        facetNames.add(facet.facetName)
    }

    override fun getIntegerNumberFacetValue(facet: ManualMandatoryIntegerNumberFacetDescriptor): Int {
        return mandatoryNumberFacets[facet]?.facetValue ?: buildFacetNotFoundException(facet)
    }

    override fun getIntegerNumberFacetValue(facet: ManualOptionalIntegerNumberFacetDescriptor): Int? {
        return optionalNumberFacets[facet]?.facetValue
    }

    override fun getTextFacetValue(facet: ManualMandatoryTextFacetDescriptor): String {
        return mandatoryTextFacets[facet]?.facetValue ?: buildFacetNotFoundException(facet)
    }

    override fun getTextFacetValue(facet: ManualOptionalTextFacetDescriptor): String? {
        return optionalTextFacets[facet]?.facetValue
    }

    override fun getConceptReferenceFacetValue(facet: ManualMandatoryConceptReferenceFacetDescriptor): ConceptIdentifier {
        return mandatoryConceptReferenceFacets[facet]?.facetValue ?: buildFacetNotFoundException(facet)
    }

    override fun getConceptReferenceFacetValue(facet: ManualOptionalConceptReferenceFacetDescriptor): ConceptIdentifier? {
        return optionalConceptReferenceFacets[facet]?.facetValue
    }

    override fun getFacetNames(): Set<FacetName> {
        return facetNames.toSet()
    }

    private fun buildFacetNotFoundException(facet: ManualFacetDescriptor<*, *>): Nothing {
        throw IllegalStateException("Facet not found for facet name '${facet.facetName}'.")
    }


}
