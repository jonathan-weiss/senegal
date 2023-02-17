package ch.cassiamon.engine.model.graph

import ch.cassiamon.engine.model.types.ConceptReferenceFacetValue
import ch.cassiamon.engine.model.types.FacetValue
import ch.cassiamon.engine.model.types.IntegerNumberFacetValue
import ch.cassiamon.engine.model.types.TextFacetValue
import ch.cassiamon.pluginapi.FacetName
import ch.cassiamon.pluginapi.model.ConceptIdentifier
import ch.cassiamon.pluginapi.model.FacetValues

class FacetValuesImpl(val facetValuesMap: Map<FacetName, FacetValue>): FacetValues {

    override fun allFacetNames(): Set<FacetName> {
        return facetValuesMap.keys
    }

    override fun asString(facetName: FacetName): String {
        return (mandatoryFacetValue(facetName) as TextFacetValue).text
    }

    override fun asInt(facetName: FacetName): Int {
        return (mandatoryFacetValue(facetName) as IntegerNumberFacetValue).number
    }

    override fun asConceptIdentifier(facetName: FacetName): ConceptIdentifier {
        return (mandatoryFacetValue(facetName) as ConceptReferenceFacetValue).conceptReference
    }

    private fun mandatoryFacetValue(facetName: FacetName): FacetValue {
        return requireNotNull(facetValuesMap[facetName])
    }

}
