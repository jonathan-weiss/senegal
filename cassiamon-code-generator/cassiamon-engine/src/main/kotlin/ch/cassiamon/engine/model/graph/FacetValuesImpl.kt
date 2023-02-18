package ch.cassiamon.engine.model.graph

import ch.cassiamon.engine.model.types.ConceptReferenceFacetValue
import ch.cassiamon.engine.model.types.FacetValue
import ch.cassiamon.engine.model.types.IntegerNumberFacetValue
import ch.cassiamon.engine.model.types.TextFacetValue
import ch.cassiamon.pluginapi.FacetName
import ch.cassiamon.pluginapi.model.ConceptIdentifier
import ch.cassiamon.pluginapi.model.FacetValues
import ch.cassiamon.pluginapi.model.exceptions.MissingFacetValueModelException
import ch.cassiamon.pluginapi.model.exceptions.WrongTypeForFacetValueModelException

class FacetValuesImpl(val facetValuesMap: Map<FacetName, FacetValue>): FacetValues {

    override fun allFacetNames(): Set<FacetName> {
        return facetValuesMap.keys
    }

    override fun asString(facetName: FacetName): String {
        return (mandatoryFacetValue(facetName, TextFacetValue::class.java)).text
    }

    override fun asInt(facetName: FacetName): Int {
        return (mandatoryFacetValue(facetName, IntegerNumberFacetValue::class.java)).number
    }

    override fun asConceptIdentifier(facetName: FacetName): ConceptIdentifier {
        return (mandatoryFacetValue(facetName, ConceptReferenceFacetValue::class.java)).conceptReference
    }

    private inline fun <reified T: FacetValue> mandatoryFacetValue(facetName: FacetName, clazz: Class<T>): T {
        val facetValue = facetValuesMap[facetName] ?: throw MissingFacetValueModelException(facetName.name)

        if (facetValue is T) {
            return facetValue
        } else {
            throw WrongTypeForFacetValueModelException("Expected: $clazz, Actual: ${facetValue.javaClass}")
        }
    }

}
