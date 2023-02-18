package ch.cassiamon.engine.model.graph

import ch.cassiamon.engine.model.inputsource.ModelConceptInputDataEntry
import ch.cassiamon.engine.model.inputsource.ModelInputData
import ch.cassiamon.engine.model.types.ConceptReferenceFacetValue
import ch.cassiamon.engine.model.types.FacetValue
import ch.cassiamon.engine.model.types.IntegerNumberFacetValue
import ch.cassiamon.engine.model.types.TextFacetValue
import ch.cassiamon.engine.schema.types.*
import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName
import ch.cassiamon.pluginapi.model.ConceptIdentifier
import ch.cassiamon.pluginapi.model.FacetValues
import ch.cassiamon.pluginapi.model.exceptions.ConceptCyclicLoopDetectedModelException

object ModelCalculator {

    fun calculateModel(schema: Schema, modelInputData: ModelInputData): CalculatedModel {
        modelInputData.entries.forEach { ModelNodeValidator.validateSingleEntry(schema, it) }

        val resolvedEntries: MutableMap<ConceptIdentifier, CalculatedModelConceptNode> = mutableMapOf()
        val unresolvedEntries: MutableList<ModelConceptInputDataEntry> = modelInputData.entries.toMutableList()


        var passes = 0
        do {
            passes++
            println("Run pass $passes")
            val unresolvedEntriesIterator = unresolvedEntries.iterator()
            val unresolvedEntriesSizeBefore = unresolvedEntries.size
            while(unresolvedEntriesIterator.hasNext()) {
                val entry = unresolvedEntriesIterator.next()
                println("Inspecting entry $entry")
                if(allReferencesResolvable(entry, resolvedEntries.keys)) {
                    println("Entry is resolvable: $entry")
                    resolvedEntries[entry.conceptIdentifier] = calculateModelNode(schema, entry)
                    unresolvedEntriesIterator.remove()
                } else {
                    println("Entry is NOT resolvable: $entry")
                }
            }
        } while (unresolvedEntries.size > 0 && hasNoCyclicDependencies(unresolvedEntries, unresolvedEntriesSizeBefore))

        return CalculatedModel(resolvedEntries)
    }

    private fun hasNoCyclicDependencies(unresolvedEntries: List<ModelConceptInputDataEntry>, sizeBefore: Int): Boolean {
        if(unresolvedEntries.size == sizeBefore) {
            throw ConceptCyclicLoopDetectedModelException(unresolvedEntries.toString())
        }
        return true
    }

    private fun allReferencesResolvable(entry: ModelConceptInputDataEntry, resolvedKeys: Set<ConceptIdentifier>): Boolean {
        if(entry.parentConceptIdentifier != null && !resolvedKeys.contains(entry.parentConceptIdentifier)) {
            return false;
        }

        return entry.facetValuesMap
            .filter { it.value is ConceptReferenceFacetValue }
            .map { Pair(it.key, it.value as ConceptReferenceFacetValue) }
            .all { resolvedKeys.contains(it.second.conceptReference)}
    }

    private fun calculateModelNode(
        schema: Schema,
        entry: ModelConceptInputDataEntry,
    ): CalculatedModelConceptNode {

        val schemaConcept: Concept = schema.conceptByConceptName(entry.conceptName)

        val facetValues = entry.facetValuesMap.toMutableMap()

        schemaConcept.facets
            .forEach { facet ->
                calculateFacetAndUpdateValueMap(
                    schemaFacet = facet,
                    conceptName = entry.conceptName,
                    conceptIdentifier = entry.conceptIdentifier,
                    facetValuesMap = facetValues,
                )
            }

        return CalculatedModelConceptNode(
            conceptName = entry.conceptName,
            conceptIdentifier = entry.conceptIdentifier,
            parentConceptIdentifier = entry.parentConceptIdentifier,
            facetValues = FacetValuesImpl(facetValues)
        )
    }

    private fun calculateFacetAndUpdateValueMap(
        schemaFacet: Facet,
        conceptName: ConceptName,
        conceptIdentifier: ConceptIdentifier,
        facetValuesMap: MutableMap<FacetName, FacetValue>,
    ) {

        val facetName = schemaFacet.facetName
        val facetValues = FacetValuesImpl(facetValuesMap)

        val facetRestrictedConceptNode = ConceptNodeImpl(
            conceptName = conceptName,
            conceptIdentifier = conceptIdentifier,
            facetValues = keyRestrictedFacetValue(facetValuesMap, schemaFacet),
        )

        val newFacetValue: FacetValue = when(schemaFacet) {
            is TextManualFacet -> TextFacetValue(schemaFacet.facetTransformationFunction(facetRestrictedConceptNode, facetValues.asString(facetName)))
            is TextCalculatedFacet -> TextFacetValue(schemaFacet.facetCalculationFunction(facetRestrictedConceptNode))
            is IntegerNumberManualFacet -> IntegerNumberFacetValue(schemaFacet.facetTransformationFunction(facetRestrictedConceptNode, facetValues.asInt(facetName)))
            is IntegerNumberCalculatedFacet -> IntegerNumberFacetValue(schemaFacet.facetCalculationFunction(facetRestrictedConceptNode))
            is ConceptReferenceManualFacet -> ConceptReferenceFacetValue(schemaFacet.facetTransformationFunction(facetRestrictedConceptNode, facetValues.asConceptIdentifier(facetName)))
            is ConceptReferenceCalculatedFacet -> ConceptReferenceFacetValue(schemaFacet.facetCalculationFunction(facetRestrictedConceptNode))
            else -> throw IllegalStateException("The facet type $schemaFacet is not supported.")
        }

        facetValuesMap[facetName] = newFacetValue
    }

    private fun keyRestrictedFacetValue(facetValues: MutableMap<FacetName, FacetValue>,
                                        schemaFacet: Facet): FacetValues {
        return FacetValuesImpl(facetValues) // TODO implement the restrictions
    }
}
